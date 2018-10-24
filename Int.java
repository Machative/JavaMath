package aidan.math;

public class Int {
	private static String valid_digits_base10="0123456789";
	private String value;
	private String absValue;
	private String sign;
	private Int(String value){
		this.value=value;
		if(value.contains("-")) {
			this.sign="-";
			this.absValue=value.substring(1, value.length());
		}else {
			this.sign="+";
			if(value.charAt(0)=='+') this.absValue=value.substring(1,value.length());
			else this.absValue=value;
		}
		
	}
	private Int(String sign, String absValue) {
		this.sign=sign;
		this.absValue=absValue;
		this.value=sign+absValue;
	}
	public static Int createInt(String value) {
		for(int i=1;i<value.length();i++) {
			if(!(valid_digits_base10.contains(String.valueOf(value.charAt(i))))) {
				System.out.println("Int Object cannot be created with value "+value+"; contains invalid digit(s).");
				return null;
			}
		}
		if(!(valid_digits_base10.contains(String.valueOf(value.charAt(0)))) && !(value.charAt(0) =='-') && !(value.charAt(0)=='+')) {
			System.out.println("Int Object cannot be created with value "+value+"; contains invalid digit(s).");
			return null;
		}else return new Int(value);
	}
	public static Int createInt(String sign, String absValue) {
		if(!(sign.equals("+")) && !(sign.equals("-"))) {
			System.out.println("Int Object cannot be created with value "+sign+absValue+"; contains invalid sign.");
			return null;
		}
		for(int i=0;i<absValue.length();i++) {
			if(!(valid_digits_base10.contains(String.valueOf(absValue.charAt(i))))) {
				System.out.println("Int Object cannot be created with value "+sign+absValue+"; contains invalid digit(s).");
				return null;
			}
		}
		return new Int(sign,absValue);
	}
	public String getValue() {
		return this.value.replace("+","");
	}
	public String getFullValue() {
		return this.value;
	}
	public String getSign() {
		return this.sign;
	}
	public String getAbsValue() {
		return this.absValue;
	}
	public Int setValue(String value) {
		for(int i=1;i<value.length();i++) {
			if(!(valid_digits_base10.contains(String.valueOf(value.charAt(i))))) {
				System.out.println("Int Object with value "+this.value+" cannot be assigned new value of "+value+".");
				return this;
			}
		}
		if(!(valid_digits_base10.contains(String.valueOf(value.charAt(0)))) && !(value.charAt(0) =='-') && !(value.charAt(0)=='+')) {
			System.out.println("Int Object with value "+this.value+" cannot be assigned new value of "+value+".");
			return this;
		}
		localSetValue(value);
		return this;
	}
	public Int setValue(String sign, String absValue) {
		for(int i=0;i<absValue.length();i++) {
			if(!(valid_digits_base10.contains(String.valueOf(absValue.charAt(i))))) {
				System.out.println("Int Object with value "+this.value+" cannot be assigned new value of "+sign+absValue+"; invalid digit(s).");
				return this;
			}
		}
		if(!(sign.equals("-")) && !(sign.equals("+"))) {
			System.out.println("Int Object with value "+this.value+" cannot be assigned new value of "+sign+absValue+"; invalid sign.");
			return this;
		}
		localSetValue(sign+absValue);
		return this;
	}
	public Int setSign(String sign) {
		if(!(sign.equals("+"))&&!(sign.equals("-"))) {
			System.out.println("Int Object with value "+this.value+" cannot be assigned new sign of "+sign+".");
			return this;
		}else {
			localSetSign(sign);
			return this;
		}
	}
	public Int setAbsValue(String absValue) {
		for(int i=0;i<absValue.length();i++) {
			if(!(valid_digits_base10.contains(String.valueOf(absValue.charAt(i))))) {
				System.out.println("Int Object with value "+this.value+" cannot be assigned new absolute value of "+absValue+".");
				return this;
			}
		}
		localSetAbsValue(absValue);
		return this;
	}
	private void localSetValue(String value) {
		this.value=value;
		if(!(value.charAt(0)=='+') && !(value.charAt(0)=='-')) {
			this.sign="+";
		}else {
			this.sign=String.valueOf(value.charAt(0));
		}
		this.absValue=this.value.replace("+","").replace("-","");
	}
	private void localSetSign(String sign) {
		this.sign=sign;
		this.value=this.sign+this.absValue;
	}
	private void localSetAbsValue(String absValue) {
		this.absValue=absValue;
		this.value=this.sign+absValue;
	}
	public static Int pos_one() {
		return new Int("+","1");
	}
	public static Int neg_one() {
		return new Int("-","1");
	}
	public static Int zero() {
		return new Int("0");
	}
}
