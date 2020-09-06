package Servlet.FileServlet;


import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(
        name = "FileUploadTest",
        urlPatterns = {"/FileUploadTest"}
)
@MultipartConfig(
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 50
)
public class FileUploadServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");

        PrintWriter writer = resp.getWriter();
        writer.append("<!DOCTYPE html>\r\n")
                .append("<html>\r\n")
                .append("    <head>\r\n")
                .append("        <title>파일 업로드 테스트 페이지</title>\r\n")
                .append("    </head>\r\n")
                .append("    <body>\r\n")
                .append("        <h1>FileUpload 테스트</h1>\n")
                .append("        <form action=\"FileUploadTest\" method=\"post\" enctype=\"multipart/form-data\">\n")
                .append("        작성자  <input type=\"text\" name=\"fileWriter\"><br/><br/>\n")
                .append("        파일<input type=\"file\" name=\"fileName\"><br/><br/>\n")
                .append("        파일설명<br/><textarea name=\"fileDescription\" rows=\"5\" cols=\"30\">")
                .append("               </textarea><br/><br/>\r\n")
                .append("        <input type = \"submit\" value=\"업로드\">\n")
                .append("    </form>")
                .append("    </body>\r\n")
                .append("</html>\r\n");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        // 파일 작성자
        String fileWriter = req.getParameter("fileWriter");
        // 파일 설명
        String fileDescription = req.getParameter("fileDescription");
        // 파일 이름
        Part part = req.getPart("fileName");
        String fileName = getFilename(part);
        if (!fileName.isEmpty()) {
            part.write("C:\\uploadTest\\"+fileName);
        }


        // 응답 작성
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = resp.getWriter();

        writer.print("작성자: " + fileWriter + "<br>");
        writer.print("파일명:<a href='FileDownloadTest?fileName=" + fileName + "'> " + fileName + "</a href><br>"); // 다운로드 추가
        writer.print("파일설명: "+ fileDescription + "<br>"); // 다운로드 추가
        writer.print("파일크기: " + part.getSize() + " bytes" + "<br>");
    }

    private String getFilename(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        System.out.println(" LOG :: content-disposition 헤더 :: = " + contentDisp);
        String[] tokens = contentDisp.split(";");
        for (int i = 0; i < tokens.length; i++) {
            String token = tokens[i];
            if (token.trim().startsWith("filename")) {
                System.out.println(" LOG :: 파일 이름 :: " + token);
                return token.substring(token.indexOf("=") + 2, token.length() - 1);
            }
        }
        return "";
    }
}
