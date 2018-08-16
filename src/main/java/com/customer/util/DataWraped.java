package com.customer.util;

import java.io.Serializable;
import com.customer.util.ExceptionCode.*;
import org.springframework.stereotype.Repository;


/**
 * 
 * @ClassName: BaseDao
 * @Description: 添加统一dao结果集返回obj BaseDao<T>
 * @date 2017-11-30 上午9:34:55
 * @author Jun
 * @param <T>
 */

@Repository
public class DataWraped implements Serializable {

	@Override
	public String toString() {
		return "DataWraped [resultMsg=" + resultMsg + ", resultCode="
				+ resultCode + ", subErrorCode=" + subErrorCode
				+ ", serverTimeStamp=" + serverTimeStamp + ", requestId="
				+ requestId + ", operationNumber=" + operationNumber
				+ ", data=" + data + "]";
	}

	/**
	 * @Fields serialVersionUID :
	 */
	private static final long serialVersionUID = 6422759391350012588L;
	private String resultMsg;
	private ExceptionCode.ResultCode resultCode;
	private String subErrorCode;
	private String serverTimeStamp;
	private String requestId;
	private int operationNumber;
	private int operationSum;
	private String xfResultCode;
	// private T data;
	private Object data;

	public DataWraped() {
		resultMsg = "";
		resultCode = ResultCode.NO_ERROR;
		subErrorCode = "0";
		operationNumber = 0;
		data = null;
	}

	public String getXfResultCode() {
        return xfResultCode;
    }

    public void setXfResultCode(String xfResultCode) {
        this.xfResultCode = xfResultCode;
    }

    public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String errorMsg) {
		this.resultMsg = errorMsg;
	}

	public ResultCode getResultCode() {
		return resultCode;
	}

	public void setResultCode(ResultCode resultCode) {
		this.resultCode = resultCode;
	}

	public String getSubErrorCode() {
		return subErrorCode;
	}

	public void setSubErrorCode(String subErrorCode) {
		this.subErrorCode = subErrorCode;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getServerTimeStamp() {
		return serverTimeStamp;
	}

	public void setServerTimeStamp(String string) {
		this.serverTimeStamp = string;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public int getOperationNumber() {
		return operationNumber;
	}

	public void setOperationNumber(int operationNumber) {
		this.operationNumber = operationNumber;
	}

    public int getOperationSum() {
        return operationSum;
    }

    public void setOperationSum(int operationSum) {
        this.operationSum = operationSum;
    }
	
	
}
