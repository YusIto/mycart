package app;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class DepartmentAddServlet
 */
@WebServlet("/EmployeeEditServlet")
public class EmployeeEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeeEditServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());

	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("呼ばれました");
		// JDBCドライバの準備
		try {
		// JDBCドライバのロード
		Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
		// ドライバが設定されていない場合はエラーになります
		throw new RuntimeException(String.format("JDBCドライバのロードに失敗しました。詳細:[%s]",
		e.getMessage()), e);
		}

		// アクセス元のHTMLでｑに設定された値を取得して、String型の変数idに代入
		String syainId = request.getParameter("syainId");
		// 商品名
		String syainName = request.getParameter("syainName");

		String syainAge = request.getParameter("syainAge");

		String syainSex = request.getParameter("syainSex");

		String syainPostalcode = request.getParameter("syainPostalcode");

		String syainPrefecture = request.getParameter("syainPrefecture");

		String syainAddress = request.getParameter("syainAddress");

//		String syainDepartmentId = request.getParameter("syainDepartmentid");

		String syainDepartmentName = request.getParameter("syainDepartmentname");
		System.out.println("部署名は"+syainDepartmentName);

		String syainNyuusya = request.getParameter("syainNyuusya");

		String syainTaisya = request.getParameter("syainTaisya");

		// データベースにアクセスするために、データベースのURLとユーザ名とパスワードを指定します
		// ※SQLのログを出力するため変数urlの値は基本的な形式から少し変更を加えています。
		// そのためシステム構築2で使い回すときは注意下さい！
		String url = "jdbc:log4jdbc:oracle:thin:@localhost:1521:XE";
		String user = "webapp";
		String pass = "webapp";

		//実行するSQL文 --String sql

		String sql ="update MS_SYAIN  \n" +
				"set SYAIN_NAME ='"+syainName+"', SYAIN_AGE ="+syainAge+", SYAIN_SEX='"+syainSex+"', SYAIN_POSTALCODE ='"+syainPostalcode+"', SYAIN_PREFECTURE='"+syainPrefecture+"',  \n" +
				"SYAIN_ADDRESS='"+syainAddress+"', SYAIN_NYUUSYA= '"+syainNyuusya+"',SYAIN_TAISYA= '"+syainTaisya+"',  \n" +
				"MS_SYAIN.DEPARTMENT_ID =(select MS_DEPARTMENT.ID from MS_DEPARTMENT where MS_DEPARTMENT.DEPARTMENT_NAME ='"+syainDepartmentName+"') \n where SYAIN_ID= "+syainId+" \n";

		//確認
		System.out.println(sql);

		// エラーが発生するかもしれない処理はtry-catchで囲みます
		// この場合はDBサーバへの接続に失敗する可能性があります
		try (
				// データベースへ接続します
				Connection con = DriverManager.getConnection(url, user, pass);

				// SQLの命令文を実行するための準備をおこないます
				Statement stmt = con.createStatement();) {
			// SQLの命令文を実行し、その件数をint型のresultCountに代入します
			int resultCount = stmt.executeUpdate(sql);

			// アクセスした人に応答するためのJSONを用意する
			PrintWriter pw = response.getWriter();

			// JSONで出力する
			pw.append(new ObjectMapper().writeValueAsString("The upadate was done."));


			System.out.println(sql);

		} catch (Exception e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細：[%s]", e.getMessage()), e);
		}
	}

}
