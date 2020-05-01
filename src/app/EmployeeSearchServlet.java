package app;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class EmployeeSearchServlet
 */
@WebServlet("/EmployeeSearchServlet")
public class EmployeeSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EmployeeSearchServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// データベースにアクセスするために、データベースのURLとユーザ名とパスワードを指定します
		// ※SQLのログを出力するため変数urlの値は基本的な形式から少し変更を加えています。
		// そのためシステム構築2で使い回すときは注意下さい！
		String url = "jdbc:log4jdbc:oracle:thin:@localhost:1521:XE";
		String user = "webapp";
		String pass = "webapp";


//	JSでは… var requestQuery = {
//		syainDepartmentname : inputSyainDepartment,
//		syainId : inputSyainId,
//		syainName : inputSyainName
//		String型の変数に代入

		String departmentName =request.getParameter("syainDepartmentname");
		String syainId =request.getParameter("syainId");
		String syainName = request.getParameter("syainName");


		//実行するSQL文 --String sql

		String sql = "select MS_SYAIN.SYAIN_ID \n" +
					", MS_SYAIN.SYAIN_NAME \n" +
					" from MS_SYAIN";
		if(!departmentName.equals("")){
				sql+=", MS_DEPARTMENT \n";
		}
			sql +=	" where 1=1 \n";

		if(!departmentName.equals("")){
		sql +=" and  MS_DEPARTMENT.DEPARTMENT_NAME = '"+ departmentName
				+"' \n" + " and MS_SYAIN.DEPARTMENT_ID=MS_DEPARTMENT.ID \n";
		}
		if(!syainId.equals("")){
		sql +=" and MS_SYAIN.SYAIN_ID = "+ syainId +" \n" ;
		}if(!syainName.equals("")){
		sql +=" and MS_SYAIN.SYAIN_NAME like '"+ syainName + "%' \n";
		}
		sql +="order by MS_SYAIN.SYAIN_ID \n";
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
			ResultSet rs1 = stmt.executeQuery(sql);

			// 情報を保持するため、Department型の変数dptを宣言
			// 変数dptはJSPに渡すための社員情報を保持させます
			// Department dpt = new Department();

			List<Employee> empList = new ArrayList<>();

			// SQL実行結果を保持している変数rs1から部署情報を取得
			// rs1.nextは取得した部署情報表に次の行があるとき、trueになります
			// 次の行がないときはfalseになります
			while (rs1.next()) {

				Employee emp = new Employee();
				emp.setId(rs1.getString("SYAIN_ID")); // 社員IDを変数empに代入
				emp.setName(rs1.getString("SYAIN_NAME"));// SQL実行結果のsyainname列の値を取得し変数empに代入します
				empList.add(emp);
			}

			// 確認用にsysout
			System.out.println(empList);

			// アクセスした人に応答するためのJSONを用意する
			PrintWriter pw = response.getWriter();

			// JSONで出力する
			pw.append(new ObjectMapper().writeValueAsString(empList));

		}catch(

	Exception e)
	{
		throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細：[%s]", e.getMessage()), e);
	}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
