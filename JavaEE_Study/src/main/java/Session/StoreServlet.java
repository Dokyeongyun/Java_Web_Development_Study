package Session;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

@WebServlet(
        name = "storeServlet",
        urlPatterns = {"/shop"}
)
public class StoreServlet extends HttpServlet {

    private final Map<Integer, String> products = new Hashtable<>();

    public StoreServlet() {
        this.products.put(1, "꼬깔콘");
        this.products.put(2, "빅파이");
        this.products.put(3, "콘칩");
        this.products.put(4, "새우깡");
        this.products.put(5, "홈런볼");
        this.products.put(6, "감자깡");
        this.products.put(7, "고구마깡");
        this.products.put(8, "꼬북칩");
        this.products.put(9, "양파링");
        this.products.put(10, "꽃게랑");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if (action == null) {
            action = "productList";
        }

        switch (action) {
            case "addToCart":
                this.addToCart(req, resp);
                break;
            case "viewCart":
                this.viewCart(req, resp);
                break;
            case "emptyCart":
                this.emptyCart(req, resp);
                break;
            case "changeSessionId":
                this.changeSessionId(req, resp);
                break;
            case "changeCart":
                this.changeCart(req, resp);
                break;
            case "productList":
            default:
                this.productList(req, resp);
                break;
        }
    }

    private void viewCart(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("products", this.products);
        req.getRequestDispatcher("/WEB-INF/jsp/view/viewCart.jsp").forward(req, resp);
    }

    private void productList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("products", this.products);
        req.getRequestDispatcher("/WEB-INF/jsp/view/productList.jsp").forward(req, resp);
    }

    private void addToCart(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int productId;

        // 장바구니에 추가할 상품의 ID 얻기
        try {
            productId = Integer.parseInt(req.getParameter("productId"));
        } catch (Exception e) {
            resp.sendRedirect("shop");
            return;
        }

        // getSession() : 세션이 존재하면 세션 반환, 존재하지 않으면 새로운 세션 생성하는 getSession(true) 호출
        HttpSession session = req.getSession();
        // getAttribute() : 세션에 저장된 객체 반환
        if (session.getAttribute("cart") == null) {
            // setAttribute() : 세션과 객체를 연결
            session.setAttribute("cart", new Hashtable<Integer, Integer>());
        }

        Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cart");
        // cart 에 상품이 없는 경우 수량 0개로 상품 추가
        if (!cart.containsKey(productId)) {
            cart.put(productId, 0);
        }
        // 상품 수량 1개 추가
        cart.put(productId, cart.get(productId) + 1);

        resp.sendRedirect("shop?action=viewCart");
    }

    private void emptyCart(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        // 장바구니 비우기
        req.getSession().removeAttribute("cart");
        resp.sendRedirect("shop?action=viewCart");
    }

    private void changeSessionId(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.changeSessionId();
        resp.sendRedirect("shop");
    }

    private void changeCart(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        HttpSession session = req.getSession();
        if (session.getAttribute("cart") != null) {
            Map<Integer,Integer> newMap = new Hashtable<>();
            session.setAttribute("cart", newMap);
        }

        resp.sendRedirect("shop?action=viewCart");
    }
}
