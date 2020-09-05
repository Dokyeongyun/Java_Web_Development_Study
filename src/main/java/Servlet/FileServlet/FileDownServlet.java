package Servlet.FileServlet;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;

@WebServlet(
        name = "FileDownloadTest",
        urlPatterns = {"/FileDownloadTest"}
)
public class FileDownServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String fileName = req.getParameter("fileName");

        // 서버에 올라간 경로 가져오기
        String downloadPath = ("C:\\uploadTest\\");
        String filePath = downloadPath + fileName;


        System.out.println(" LOG [업로드된 파일 경로] :: " + downloadPath);
        System.out.println(" LOG [파일 전체 경로] :: " + filePath);


        // 파일 이름이 한글일 경우를 대비하여 UTF-8로 인코딩함
        String encodingFileName = new String(fileName.getBytes("euc-kr"));

        byte[] b = new byte[4096];
        FileInputStream fileInputStream = new FileInputStream(filePath);

        String sMimeType = getServletContext().getMimeType(filePath);

        if(sMimeType == null) sMimeType = "application/octet-stream";

        resp.setContentType(sMimeType);

        resp.setHeader("Content-Disposition", "attachment; filename=" + encodingFileName);

        ServletOutputStream stream = resp.getOutputStream();
        int read;
        while ((read = fileInputStream.read(b, 0, b.length)) != -1) {
            stream.write(b, 0, read);
        }
    }
}
