package my.rpc.core.common;

/**
 * @Classname RpcInvocation
 * @Date 2023/6/26 15:05
 * @Created by mao
 * @Description
 */
public class RpcInvocation {

    // 请求的目标方法
    private String targetMethod;

    // 请求的目标服务名称
    private String targetServiceName;

    // 请求的参数信息
    private Object[] args;

    private String uuid;

    // 接口相应的数据塞入这个字段中（如果异步调用或者void类型，这里就为空）
    private Object response;


    public String getTargetMethod() {
        return targetMethod;
    }

    public void setTargetMethod(String targetMethod) {
        this.targetMethod = targetMethod;
    }

    public String getTargetServiceName() {
        return targetServiceName;
    }

    public void setTargetServiceName(String targetServiceName) {
        this.targetServiceName = targetServiceName;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }
}
