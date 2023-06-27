package my.rpc.core.server;

import my.rpc.interfaces.DataService;

/**
 * @Classname DataServiceImpl
 * @Date 2023/6/27 14:47
 * @Created by mao
 * @Description
 */
public class DataServiceImpl implements DataService {

    @Override
    public String sendData(String body) {
        System.out.println("已收到参数长度：" + body.length());
        return "success";
    }
}
