package my.rpc.core.common;

import java.io.Serial;
import java.io.Serializable;

import static my.rpc.core.common.constants.RpcConstants.MAGIC_NUMBER;

/**
 * @Classname RpcProtocol
 * @Date 2023/6/26 14:47
 * @Created by mao
 * @Description
 */
public class RpcProtocol implements Serializable {


    @Serial
    private static final long serialVersionUID = 1686550646623792079L;


    private short magicNumber = MAGIC_NUMBER;

    private int contentLength;

    private byte[] content;


    public RpcProtocol(byte[] content) {
        this.content = content;
        this.contentLength = content.length;
    }

    public short getMagicNumber() {
        return magicNumber;
    }

    public void setMagicNumber(short magicNumber) {
        this.magicNumber = magicNumber;
    }

    public int getContentLength() {
        return contentLength;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
