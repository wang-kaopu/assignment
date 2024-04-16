import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

public class ConnectionPool {
    /*
        连接池的配置
     */
    private final int initSize;//初始化连接数量
    private final int maxSize;//最大连接数量
    private final int autoGrowSize;//自动增长数量

    /*
        连接池运行时的参数
     */

    //1. 当前活跃的连接的个数
    //AtomicInteger类的原子性，线程同步
    private AtomicInteger currentActive = new AtomicInteger(0);
    //2. 存放当前空闲的连接的集合
    //Vector加强线程安全
    private Vector<Connection> freePool = new Vector<>();
    //3.存放当前活跃的连接的集合
    private Vector<Connection> activePool = new Vector<>();

    /*
        构造器，初始化一个连接池
     */
    public ConnectionPool() {
        Properties properties = new Properties();
        try {
            properties.load(new FileReader("src\\ConnectionPoolConfig.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        initSize = Integer.parseInt(properties.getProperty("initSize"));
        maxSize = Integer.parseInt(properties.getProperty("maxSize"));
        autoGrowSize = Integer.parseInt(properties.getProperty("autoGrowSize"));
        for(int i = 0;i<initSize;i++){
            freePool.add(createConnection());
        }
    }

    /*
        创建新连接的方法
     */
    private Connection createConnection(){
        Connection newConnection = DataSourceConfig.getConnection();
        currentActive.incrementAndGet();
        System.out.println("创建了一个新连接："+newConnection);
        return newConnection;
    }

    /*
        获取连接的方法
        1. 从freePool空闲池拿到一个连接
        2. 或者freePool已空，需要新建一些连接放到freePool去用
     */
    public synchronized Connection getConnection(){
        if(freePool.isEmpty()){
            //已经空了
            //是不是已经到达zhui大连接数量了？是的话就不给你新建了哦
            if(currentActive.get()<maxSize){
               //还没到最大捏
                for(int i  = 0; i < autoGrowSize && currentActive.get()<maxSize;i++){
                    Connection created = createConnection();
                    freePool.add(created);
                    System.out.println("freePool空闲池新添加了一个连接："+created);
                }
            }else{
                //到了最大了
                System.out.println("已经到达最大连接数量："+maxSize);
            }
        }
        //还没空，直接在freePool拿一个走
        Connection connGotFromFreePool = freePool.get(0);
        freePool.remove(0);
        System.out.println("从freePool拿走了一个连接："+connGotFromFreePool);
        activePool.add(connGotFromFreePool);
        System.out.println("为activePool添加了一个连接："+connGotFromFreePool);
        return connGotFromFreePool;
    }

    /*
        回收连接的方法
     */
    public synchronized void releaseConnection(Connection conn){
        try {
            if(!conn.isClosed()&&conn!=null){
                //判断传进来的连接还没有被关掉且非空
                //送到freePool里等待重新利用
                freePool.add(conn);
                //把连接从activePool里remove掉
                for (Connection c : activePool) {
                    if(c.equals(conn)){
                        activePool.remove(c);
                        break;
                    }
                }
                //输出一下、
                System.out.println("回收了一个连接："+conn
                        +"，现在的空闲连接数是："+freePool.size()+
                        "，活跃的连接数是："+currentActive);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
