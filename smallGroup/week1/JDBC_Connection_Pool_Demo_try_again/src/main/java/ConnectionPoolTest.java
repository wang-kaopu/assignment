import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class ConnectionPoolTest {
    public static void main(String[] args) throws SQLException {
        ConnectionPool connectionPool = new ConnectionPool();
        /*
            查询测试
         */
        for(int i = 0;i<15;i++) {
            QueryCallable queryCallable = new QueryCallable(connectionPool, "select * from students;");
            FutureTask<ResultSet> task = new FutureTask<>(queryCallable);
            Thread thread = new Thread(task);
            thread.start();
            try {
                ResultSet rs;
                if((rs = task.get())!=null){
                    while (rs.next()){
                        int id = rs.getInt("id");
                        String name = rs.getString("name");
                        System.out.print(id);
                        System.out.println("："+name);
                    }
                    if(!rs.isClosed()){
                        rs.close();
                    }
                }
            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        /*
            插入测试
         */
        for(int i = 0;i<15;i++){
            InsertCallable insertCallable = new InsertCallable(connectionPool, "insert into students (id,name) values (3,'wangwu');");
            FutureTask<Integer> task = new FutureTask<>(insertCallable);
            new Thread(task).start();
            try {
                Integer update = task.get();
                System.out.println(update);
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
    }
}