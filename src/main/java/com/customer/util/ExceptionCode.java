package com.customer.util;
  
/**
 * 
* @ClassName: ExceptionCode 
* @Description: 异常处理枚举类
* @date 2017-11-30 下午3:34:33
* @author Jun
 */
public class ExceptionCode {

	static public enum ResultCode {
		 NO_ERROR(0),
		 INNER_ERROR(1),
		 OP_ERROR(2);
		 private final int iEx;
         private ResultCode(final int iEx){
             this.iEx=iEx;
         }
         
         @Override
         public String toString(){
             return String.valueOf(iEx);
         }
	}
	
	/**
	 * 
	* @ClassName: DbExCode 
	* @Description: 
	* @date 2017-11-30 下午3:51:48
	 */
	static public enum DbExCode {
		 DB_ACCESS_EX("0");
		 private final String text;
         private DbExCode(final String text){
             this.text=text;
         }
         
         @Override
         public String toString(){
             return text;
         }
	}
	/**
	 * 
	* @ClassName: WbServiceExCode 
	* @Description: WebService接口枚举异常类
	* @date 2017-11-30 下午3:51:01
	* @author Jun
	 */
	static public enum WbServiceExCode {
		 WEB_SERVICE_EX("2");
		 private final String text;
        private WbServiceExCode(final String text){
            this.text=text;
        }
        
        @Override
        public String toString(){
            return text;
        }
	}
	
}
