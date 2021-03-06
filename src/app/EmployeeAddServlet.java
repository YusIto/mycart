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
@WebServlet("/EmployeeAddServlet")
public class EmployeeAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeeAddServlet() {
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

//		String sql = "insert into MS_SYAIN (SYAIN_ID, SYAIN_NAME, SYAIN_AGE, SYAIN_SEX, SYAIN_POSTALCODE, SYAIN_PREFECTURE, SYAIN_ADDRESS, DEPARTMENT_ID, SYAIN_NYUUSYA,SYAIN_TAISYA) "select "+syainId+", '"+syainName+"', "+syainAge+", '"+syainSex+"', '"+syainPostalcode+"', '"+syainAddress+"', '"+syainPrefecture+"', MS_DEPARTMENT.ID, '"+syainNyuusya+"','"+syainTaisya+"' from MS_DEPARTMENT where MS_DEPARTMENT.DEPARTMENT_NAME ='"+syainDepartmentName +"';";
		String sql = "insert into MS_SYAIN (SYAIN_ID, SYAIN_NAME, SYAIN_AGE, SYAIN_SEX, SYAIN_POSTALCODE, SYAIN_PREFECTURE,  \n" +
		"SYAIN_ADDRESS, DEPARTMENT_ID, SYAIN_NYUUSYA,SYAIN_TAISYA)  \n" +
		"select "+syainId +", '"+syainName +"', "+syainAge +", '"+ syainSex+"', '"+syainPostalcode +"', '"+syainPrefecture +"',  \n" +
		"'"+syainAddress +"', MS_DEPARTMENT.ID, '"+syainNyuusya +"','"+syainTaisya +"'  \n" +
		"from MS_DEPARTMENT  \n" +
		"where MS_DEPARTMENT.DEPARTMENT_NAME ='"+syainDepartmentName +"' \n";
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
			pw.append(new ObjectMapper().writeValueAsString("Added"));


			System.out.println(sql);

		} catch (Exception e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細：[%s]", e.getMessage()), e);
		}
	}

}

