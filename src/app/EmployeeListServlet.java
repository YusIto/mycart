package app;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/EmployeeListServlet")
public class EmployeeListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EmployeeListServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 文字化け
		response.setContentType("text/html;charset=UTF-8");

		// セッションを使います
		HttpSession session = request.getSession(true);

		// レスポンスの準備
		PrintWriter pw = response.getWriter();

		//
		// // セッションにユーザーが保存されてない（ログインしてない）、もしくは画面から送られてくるユーザーコードと違う場合はエラー
		// if (sessionempId == null) {
		// responseBase.setResult("ng");
		// responseBase.setErrorCd(ErrorConst.ERR_SESSION);
		// responseBase.setErrorMessage("ユーザーがログインしていません");
		// // レスポンスデータを書き込む
		// pw.append(new ObjectMapper().writeValueAsString(responseBase));
		// // 処理終了
		// return;
		//
		// } else if (!sessionempId.equals(userCd)) {
		// responseBase.setResult("ng");
		// responseBase.setErrorCd(ErrorConst.ERR_WRONG_USER);
		// responseBase.setErrorMessage("ユーザーが不正です。");
		// // レスポンスデータを書き込む
		// pw.append(new ObjectMapper().writeValueAsString(responseBase));
		// // 処理終了
		// return;
		// }

		// セッションでログインチェック

		String status = (String)session.getAttribute("empId");

		//ロールを取得
		String role = (String) session.getAttribute("role");

		// うえのstatusの確認
		System.out.println(status);

		// 返却データを作成、Mapに格納するため用意
		Map<String, Object> responseData = new HashMap<>();

		responseData.put("empId", status);
		responseData.put("role", role);

		if (status == null) {
			pw.append(new ObjectMapper().writeValueAsString("ログインして下さい。URL = http://localhost:8080/myCart/login.html"));

		} else {

//			String role = (String) session.getAttribute("role");
			if (role.equals("MEMBER")) {
				System.out.println("一般社員の社員一覧");

				// データベースにアクセスするために、データベースのURLとユーザ名とパスワードを指定します
				// ※SQLのログを出力するため変数urlの値は基本的な形式から少し変更を加えています。
				// そのためシステム構築2で使い回すときは注意下さい！
				String url = "jdbc:log4jdbc:oracle:thin:@localhost:1521:XE";
				String user = "webapp";
				String pass = "webapp";

				// エラーが発生するかもしれない処理はtry-catchで囲みます
				// この場合はDBサーバへの接続に失敗する可能性があります
				try (
						// データベースへ接続します
						Connection con = DriverManager.getConnection(url, user, pass);

						// SQLの命令文を実行するための準備をおこないます
						Statement stmt = con.createStatement();

						// SQLの命令文を実行し、その結果をResultSet型のrsに代入します
						ResultSet rs1 = stmt.executeQuery(
								"select MS_SYAIN.SYAIN_ID, MS_SYAIN.SYAIN_NAME, MS_SYAIN.SYAIN_AGE, MS_SYAIN.SYAIN_SEX, MS_SYAIN.SYAIN_POSTALCODE, MS_SYAIN.SYAIN_PREFECTURE, MS_SYAIN.SYAIN_ADDRESS, MS_DEPARTMENT.DEPARTMENT_NAME, MS_SYAIN.SYAIN_NYUUSYA, MS_SYAIN.SYAIN_TAISYA from MS_SYAIN, MS_DEPARTMENT where 1=1 and  MS_SYAIN.DEPARTMENT_ID = MS_DEPARTMENT.ID order by SYAIN_ID");) {

					// 情報を保持するため、Department型の変数dptを宣言
					// 変数dptはJSPに渡すための社員情報を保持させます
					// Department dpt = new Department();

					List<Employee> empList = new ArrayList<>();

					// SQL実行結果を保持している変数rsから部署情報を取得
					// rs.nextは取得した部署情報表に次の行があるとき、trueになります
					// 次の行がないときはfalseになります
					while (rs1.next()) {
						Employee emp = new Employee();
						emp.setId(rs1.getString("SYAIN_ID")); // 社員IDを変数empに代入
						emp.setName(rs1.getString("SYAIN_NAME"));// SQL実行結果のsyainname列の値を取得し変数empに代入します
						empList.add(emp);
					}

					// 確認用にsysout
					System.out.println(empList);

					responseData.put("data", empList);

					// JSONで出力する
					pw.append(new ObjectMapper().writeValueAsString(responseData));

				} catch (Exception e) {
					throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細：[%s]", e.getMessage()), e);

				}
			} else if (role.equals("MANEGER")) {

				// データベースにアクセスするために、データベースのURLとユーザ名とパスワードを指定します
				// ※SQLのログを出力するため変数urlの値は基本的な形式から少し変更を加えています。
				// そのためシステム構築2で使い回すときは注意下さい！
				String url = "jdbc:log4jdbc:oracle:thin:@localhost:1521:XE";
				String user = "webapp";
				String pass = "webapp";

				// エラーが発生するかもしれない処理はtry-catchで囲みます
				// この場合はDBサーバへの接続に失敗する可能性があります
				try (
						// データベースへ接続します
						Connection con = DriverManager.getConnection(url, user, pass);

						// SQLの命令文を実行するための準備をおこないます
						Statement stmt = con.createStatement();

						// SQLの命令文を実行し、その結果をResultSet型のrsに代入します
						ResultSet rs1 = stmt.executeQuery(
								"select MS_SYAIN.SYAIN_ID, MS_SYAIN.SYAIN_NAME, MS_SYAIN.SYAIN_AGE, MS_SYAIN.SYAIN_SEX, MS_SYAIN.SYAIN_POSTALCODE, MS_SYAIN.SYAIN_PREFECTURE, MS_SYAIN.SYAIN_ADDRESS, MS_DEPARTMENT.DEPARTMENT_NAME, MS_SYAIN.SYAIN_NYUUSYA, MS_SYAIN.SYAIN_TAISYA from MS_SYAIN, MS_DEPARTMENT where 1=1 and  MS_SYAIN.DEPARTMENT_ID = MS_DEPARTMENT.ID order by SYAIN_ID");) {

					// 情報を保持するため、Department型の変数dptを宣言
					// 変数dptはJSPに渡すための社員情報を保持させます
					// Department dpt = new Department();

					List<Employee> empList = new ArrayList<>();

					// SQL実行結果を保持している変数rsから部署情報を取得
					// rs.nextは取得した部署情報表に次の行があるとき、trueになります
					// 次の行がないときはfalseになります
					while (rs1.next()) {
						Employee emp = new Employee();
						emp.setId(rs1.getString("SYAIN_ID")); // 社員IDを変数empに代入
						emp.setName(rs1.getString("SYAIN_NAME"));// SQL実行結果のsyainname列の値を取得し変数empに代入します
						empList.add(emp);
					}

					// 確認用にsysout
					System.out.println(empList);

					responseData.put("data", empList);

					// JSONで出力する
					pw.append(new ObjectMapper().writeValueAsString(responseData));

				} catch (Exception e) {
					throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細：[%s]", e.getMessage()), e);
				}
			} else {
				System.out.println("今後増えるかもしれない");
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// JDBCドライバの準備
		try {
			// JDBCドライバのロード
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			// ドライバが設定されていない場合はエラーになります
			throw new RuntimeException(String.format("JDBCドライバのロードに失敗しました。詳細:[%s]", e.getMessage()), e);
		}
		// アクセス元のHTMLでsyainIdに設定された値を取得して、String型の変数idに代入
		String syainId = request.getParameter("syainId");

		// データベースにアクセスするために、データベースのURLとユーザ名とパスワードを指定します
		// ※SQLのログを出力するため変数urlの値は基本的な形式から少し変更を加えています。
		// そのためシステム構築2で使い回すときは注意下さい！
		String url = "jdbc:log4jdbc:oracle:thin:@localhost:1521:XE";
		String user = "webapp";
		String pass = "webapp";

		// 実行するSQL文 --String sql

		String sql = "delete from MS_SYAIN where SYAIN_ID =" + syainId + "\n";

		System.out.println(sql);
		// エラーが発生するかもしれない処理はtry-catchで囲みます
		// この場合はDBサーバへの接続に失敗する可能性があります
		try (
				// データベースへ接続します
				Connection con = DriverManager.getConnection(url, user, pass);

				// SQLの命令文を実行するための準備をおこないます
				Statement stmt = con.createStatement();) {

			// SQLの命令文を実行
			stmt.executeUpdate(sql);

			// アクセスした人に応答するためのJSONを用意する
			PrintWriter pw = response.getWriter();

			// JSONで出力する
			pw.append(new ObjectMapper().writeValueAsString("deleted"));
		} catch (Exception e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細：[%s]", e.getMessage()), e);
		}

	}
}
