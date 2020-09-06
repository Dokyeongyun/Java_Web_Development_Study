package Project;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

/*
  [ 고객 지원 애플리케이션 ]

   구현할 기능
     - 사용자가 질문 올리는 기능
     - 티켓을 받는 기능
     - 직원이 질문에 응답하는 기능
     - 질문, 티켓은 파일 형태로 올림 -> 파일 업로드 기능

     Version 1 : 서블릿 내에 프레젠테이션 코드를 포함시킴
     Version 2 : 서블릿에는 비즈니스 로직을, JSP 파일에 프레젠테이션 코드를 분리함
 */

/**
 * 서블릿 선언 및 URL 매핑
 */
@WebServlet(
        name = "ticketServlet",
        urlPatterns = {"/tickets"},
        loadOnStartup = 1
)

/**
 *  서블릿에 파일 업로드 기능을 제공하도록 웹 컨테이너에 지시
 *  fileSizeThreshold : 파일을 임시 디렉터리에 저장할 기준 크기
 *  maxFileSize : 한 번에 업로드 할 수 있는 파일 크기 제한
 *  maxRequestSize : 전체 요청의 크기 제한
 *  location : 임시 파일을 저장할 웹 컨테이너 지정 (작성하지 않으면 서버가 기본 임시 디렉터리 설정)
 */
@MultipartConfig(
        fileSizeThreshold = 5_242_880, // 5MB
        maxFileSize = 20_971_520L, // 20MB
        maxRequestSize = 41_943_040L // 40MB
)

public class TicketServlet extends HttpServlet {
    private volatile int TICKET_ID_SEQUENCE = 1;
    private final Map<Integer, Ticket> ticketDatabase = new LinkedHashMap<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        String action = req.getParameter("action");

        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "create":
                this.showTicketForm(req, resp);
                break;
            case "view":
                this.viewTicket(req, resp);
                break;
            case "download":
                this.downloadAttachment(req, resp);
                break;
            case "list":
            default:
                this.listTickets(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "create":
                this.createTicket(req, resp);
                break;
            case "list":
            default:
                // action 매개변수가 없거나, 잘못된 POST 를 수행할 경우 티켓을 나열하는 페이지로 리디렉션
                resp.sendRedirect("tickets");
                break;
        }
    }

    /**
     * 티켓 작성 폼
     * @param response
     * @throws IOException
     */
    private void showTicketForm(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        // Version 1 : HTML 코드를 직접 작성하여 POST
        /*
        // Title 을 작성한 후에 반환된 writer 임
        PrintWriter writer = this.writeHeader(response);

        writer.append("<h2>Create a Ticket</h2>\r\n");
        writer.append("<form method=\"POST\" action=\"tickets\" ")
                .append("enctype=\"multipart/form-data\">\r\n");
        writer.append("<input type=\"hidden\" name=\"action\" ")
                .append("value=\"create\"/>\r\n");
        writer.append("Your Name<br/>\r\n");
        writer.append("<input type=\"text\" name=\"customerName\"/><br/><br/>\r\n");
        writer.append("Subject<br/>\r\n");
        writer.append("<input type=\"text\" name=\"subject\"/><br/><br/>\r\n");
        writer.append("Body<br/>\r\n");
        writer.append("<textarea name=\"body\" rows=\"5\" cols=\"30\">")
                .append("</textarea><br/><br/>\r\n");
        writer.append("<b>Attachments</b><br/>\r\n");
        writer.append("<input type=\"file\" name=\"file1\"/><br/><br/>\r\n");
        writer.append("<input type=\"submit\" value=\"Submit\"/>\r\n");
        writer.append("</form>\r\n");

        this.writeFooter(writer);
        */

        // Version 2 : 요청 디스패처를 이용해 요청 전달
        // forward() 메서드를 통해 요청을 해당 경로로 전달 (리디렉션이 아님!)
        request.getRequestDispatcher("WEB-INF/jsp/view/showTicketForm.jsp")
                .forward(request,response);

    }

    /**
     * 개별 티켓에 대한 상세 내용을 확인하는 페이지
     * @param request
     * @param response
     * @throws IOException
     */
    private void viewTicket(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String idString = request.getParameter("ticketId");
        Ticket ticket = this.getTicket(idString, response);
        if (ticket == null)
            return;

        // Version 1 : HTML 코드를 직접 작성하여 POST
        /*
        PrintWriter writer = this.writeHeader(response);

        writer.append("<h2>Ticket #").append(idString)
                .append(": ").append(ticket.getSubject()).append("</h2>\r\n");
        writer.append("<i>Customer Name - ").append(ticket.getCustomerName())
                .append("</i><br/><br/>\r\n");
        writer.append(ticket.getBody()).append("<br/><br/>\r\n");

        if (ticket.getNumberOfAttachments() > 0) {
            writer.append("Attachments: ");
            int i = 0;
            for (Attachment attachment : ticket.getAttachments()) {
                if (i++ > 0)
                    writer.append(", ");
                 writer.append("<a href=\"tickets?action=download&ticketId=")
                        .append(idString).append("&attachment=")
                        .append(attachment.getName()).append("\">")
                        .append(attachment.getName()).append("</a>");
            }
            writer.append("<br/><br/>\r\n");
        }

        writer.append("<a href=\"tickets\">Return to list tickets</a>\r\n");

        this.writeFooter(writer);
        */

        // Version 2 : JSP 파일에서 필요한 변수를 제공하고 요청을 전달
        // 동일한 요청을 처리하는 다른 요소간에, 즉 서블릿과 JSP간에 데이터를 전달하는 방법
        request.setAttribute("ticketId",idString);
        request.setAttribute("ticket",ticket);
        request.getRequestDispatcher("/WEB-INF/jsp/view/viewTicket.jsp")
                .forward(request,response);

    }

    /**
     * 첨부된 파일을 다운로드할 수 있게 구성함
     * @param request
     * @param response
     * @throws IOException
     */
    private void downloadAttachment(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String idString = request.getParameter("ticketId");
        Ticket ticket = this.getTicket(idString, response);
        if (ticket == null)
            return;

        String name = request.getParameter("attachment");
        if (name == null) {
            response.sendRedirect("tickets?action=view&ticketId=" + idString);
            return;
        }

        Attachment attachment = ticket.getAttachment(name);
        if (attachment == null) {
            response.sendRedirect("tickets?action=view&ticketId=" + idString);
            return;
        }

        // 파일 다운로드를 클라이언트 브라우저로 제공하는 역할
        // Content-Disposition : 파일을 브라우저안에서 여는 것이 아닌, 파일 저장, 다운로드를 하게 함
        response.setHeader("Content-Disposition", "attachment; filename=" + attachment.getName());
        response.setContentType("application/octet-stream");

        // 파일 콘텐츠를 응답에 기록 (파일용량이 큰 경우 메모리 문제 발생 가능성 있음)
        ServletOutputStream stream = response.getOutputStream();
        stream.write(attachment.getContents());
    }

    /**
     * 티켓 리스트를 출력
     *
     * @param response
     * @throws IOException
     */
    private void listTickets(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        // Version 1 : HTML 코드를 직접 작성하여 POST
        /*
        // Title 을 작성한 후에 반환된 writer 임
        PrintWriter writer = this.writeHeader(response);

        writer.append("<h2>Tickets</h2>\r\n");
        writer.append("<a href=\"tickets?action=create\">Create Ticket")
                .append("</a><br/><br/>\r\n");

        if (this.ticketDatabase.size() == 0)
            // 저장된 티켓이 없다는 안내문구 출력
            writer.append("<i>There are no tickets in the system.</i>\r\n");
        else {
            // 티켓 DB에 저장되어 있는 티켓의 이름과 파일 리스트 보여주기
            // 티켓 넘버, 티켓 링크, 고객이름 출력
            for (int id : this.ticketDatabase.keySet()) {
                String idString = Integer.toString(id);
                Ticket ticket = this.ticketDatabase.get(id);
                writer.append("Ticket #").append(idString)
                        .append(": <a href=\"tickets?action=view&ticketId=")
                        .append(idString).append("\">").append(ticket.getSubject())
                        .append("</a> (customer: ").append(ticket.getCustomerName())
                        .append(")<br/>\r\n");
            }
        }
        this.writeFooter(writer);
*/

        // Version 2 : JSP 파일에서 필요한 변수를 제공하고 요청을 전달
        request.setAttribute("ticketDatabase", ticketDatabase);
        request.getRequestDispatcher("WEB-INF/jsp/view/listTickets.jsp")
                .forward(request,response);

    }

    /**
     * 티켓 생성 페이지 구성
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void createTicket(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Ticket ticket = new Ticket();
        ticket.setCustomerName(request.getParameter("customerName"));
        ticket.setSubject(request.getParameter("subject"));
        ticket.setBody(request.getParameter("body"));

        // getPart() 메서드를 통해 multipart/form-data POST 요청으로 수신받은 파트를 가져옴
        Part filePart = request.getPart("file1");
        if (filePart != null && filePart.getSize() > 0) {
            // 파일을 바이트형식으로 읽어 첨부함
            Attachment attachment = this.processAttachment(filePart);
            // 파일을 DB에 추가함
            ticket.addAttachment(attachment);
        }

        int id;
        // DB에 대한 접근을 막기 위해 synchronized 블록 사용
        synchronized (this) {
            id = this.TICKET_ID_SEQUENCE++;
            this.ticketDatabase.put(id, ticket);
        }

        // 티켓 추가 후 해당 티켓 확인페이지로 리디렉션
        response.sendRedirect("tickets?action=view&ticketId=" + id);
    }

    /**
     * 파일을 바이트형식으로 읽어 첨부하는 메서드
     *
     * @param filePart
     * @return
     * @throws IOException
     */
    private Attachment processAttachment(Part filePart) throws IOException {
        InputStream inputStream = filePart.getInputStream();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        int read;
        final byte[] bytes = new byte[1024];

        while ((read = inputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, read);
        }

        Attachment attachment = new Attachment();
        attachment.setName(filePart.getSubmittedFileName());
        attachment.setContents(outputStream.toByteArray());

        return attachment;
    }

    /**
     * DB 에서 주어진 id에 따른 티켓을 찾아 반환하는 메서드
     * @param idString
     * @param response
     * @return
     * @throws IOException
     */
    private Ticket getTicket(String idString, HttpServletResponse response) throws IOException {
        if (idString == null || idString.length() == 0) {
            response.sendRedirect("tickets");
            return null;
        }

        try {
            Ticket ticket = this.ticketDatabase.get(Integer.parseInt(idString));
            if (ticket == null) {
                response.sendRedirect("tickets");
                return null;
            }
            return ticket;
        } catch (Exception e) {
            response.sendRedirect("tickets");
            return null;
        }
    }



    // 아래 두 메서드는 Version 1 에서 사용됨 - 서블릿에서 View 코드 작성
/*

    */
/**
     * 웹 페이지의 Title 을 작성한 후 PrintWriter 를 반환
     *
     * @param response
     * @return PrintWriter
     * @throws IOException
     *//*

    private PrintWriter writeHeader(HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        PrintWriter writer = response.getWriter();
        writer.append("<!DOCTYPE html>\r\n")
                .append("<html>\r\n")
                .append("    <head>\r\n")
                .append("        <title>Customer Support</title>\r\n")
                .append("    </head>\r\n")
                .append("    <body>\r\n");

        return writer;
    }

    */
/**
     * 본문의 마지막에 공통으로 작성될 태그 - 중복을 제거하기 위한 메서드
     *
     * @param writer
     *//*

    private void writeFooter(PrintWriter writer) {
        writer.append("    </body>\r\n").append("</html>\r\n");
    }
*/

}
