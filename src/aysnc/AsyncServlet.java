package aysnc;

import javax.annotation.Resource;
import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ExecutorService;

@WebServlet(urlPatterns = {"/async"}, asyncSupported = true)
public class AsyncServlet extends HttpServlet {

    @Resource
    private ExecutorService executorService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        AsyncContext asyncContext = req.startAsync();
        final PrintWriter writer = resp.getWriter();

        executorService.submit(() -> {
            writer.println("end");
            asyncContext.complete();
        });
    }

    //    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//
//        final AsyncContext asyncContext = req.startAsync();
//
//        asyncContext.addListener(new AsyncListener() {
//            @Override
//            public void onComplete(AsyncEvent asyncEvent) throws IOException {
//                // ...
//            }
//
//            @Override
//            public void onTimeout(AsyncEvent asyncEvent) throws IOException {
//                // ...
//            }
//
//            @Override
//            public void onError(AsyncEvent asyncEvent) throws IOException {
//                // ...
//            }
//
//            @Override
//            public void onStartAsync(AsyncEvent asyncEvent) throws IOException {
//                // ...
//            }
//        });
//    }
}
