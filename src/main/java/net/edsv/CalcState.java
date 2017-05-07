package net.edsv;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.EnumMap;
import java.util.function.Function;
//2017, EdSv.
public class CalcState implements Serializable {

	private StringBuilder operand1 = new StringBuilder();
	private StringBuilder operand2 = new StringBuilder();
	private int numberCounter = 0;
	private Function<String, String> currentF;
	private boolean pointFlag = false;
	private boolean signFlag = false;
	public boolean inputOverflow = false;
	private String result ="0";
	private String hint = "!!!";
	
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getHint() {
		return hint;
	}
	public void setHint(String hint) {
		this.hint = hint;
	}
	
	public int getNumberQnt(StringBuilder op) {
		if(signFlag == true)
			return op.length() - 1;
		return op.length();
	}
	
	public String getOperand1() {
		if(operand1 == null)
			return null;
		return operand1.toString();
	}
	public void setOperand1(String operand1) {
		int l = getNumberQnt(this.operand1);
		if(l < 12) {
			this.operand1.append(operand1);
			this.inputOverflow = false;
		}else if (l >= 13) {
			this.inputOverflow = true;
		}
	}
	public String getOperand2() {
		return operand2.toString();
	}
	public void setOperand2(String operand2) {
		int l = getNumberQnt(this.operand2);
		if(l < 12) {
			this.operand2.append(operand2);
			this.inputOverflow = false;
		}else if (l >= 13) {
			this.inputOverflow = true;
		}
	}
	
	public void setOperand1(StringBuilder operand1) {
		this.operand1 = operand1;
		this.inputOverflow = false;
	}
	public void setOperand2(StringBuilder operand2) {
		this.operand2 = operand2;
		this.inputOverflow = false;
	}
	
	public StringBuilder getOperandBuilder1() {
		return this.operand1;
	}
	public StringBuilder getOperandBuilder2() {
		return this.operand2;
	}
	
	public Function<String, String> getCurrentF() {
		return currentF;
	}
	public void setCurrentF(Function<String, String> currentF) {
		this.currentF = currentF;
	}

	public boolean isPointFlag() {
		return pointFlag;
	}
	public void setPointFlag(boolean pointFlag) {
		this.pointFlag = pointFlag;
	}
	public boolean isSignFlag() {
		return signFlag;
	}
	public void setSignFlag(boolean signFlag) {
		this.signFlag = signFlag;
	}
	
	
	void resetFlags () {
			pointFlag = false;
			signFlag = false;
	}
	
	void setFlags (String opOrResult) {
		if(opOrResult.contains("."))
			pointFlag = true;
		else
			pointFlag = false;
			
		if(opOrResult.length() != 0 && opOrResult.substring(0,1).equals("-"))
			signFlag = true;
		else
			signFlag = false;
	}
	// calc operations + - * / mod 1/x sqrt %
	public String add(String a) {
		BigDecimal bd1, bd2;
		bd1 = new BigDecimal(getOperand1());
		bd2 = new BigDecimal(getOperand2());
		result = bd1.add(bd2).toPlainString();
		setFlags(result);
		return result;
	}
	
	public String sub(String a) {
		BigDecimal bd1, bd2;
		bd1 = new BigDecimal(getOperand1());
		bd2 = new BigDecimal(getOperand2());
		result = bd1.subtract(bd2).toPlainString();
		setFlags(result);//is it necessary
		return result;
	}
	
	public String multiply(String a) {
		BigDecimal bd1, bd2;
		bd1 = new BigDecimal(getOperand1());
		bd2 = new BigDecimal(getOperand2());
		result = bd1.multiply(bd2).toPlainString();
		setFlags(result);
		return result;
	}
	
	public String divide(String a) throws ArithmeticException {
		 BigDecimal bd1, bd2;
	     bd1 = new BigDecimal(getOperand1());
		 bd2 = new BigDecimal(getOperand2());
	      MathContext mc = new MathContext(2, RoundingMode.HALF_EVEN);
	      BigDecimal res = bd1.divide(bd2, MathContext.DECIMAL32).round(mc);
	      result = res.toString();
	      setFlags(result);
		return result;
	}
	
	public String mod(String a) {
		BigDecimal bd1, bd2;
		bd1 = new BigDecimal(getOperand1());
		bd2 = new BigDecimal(getOperand2());
		result = bd1.remainder(bd2).toPlainString();
		setFlags(result);
		return result;
	}

	public String reverse(String a) throws ArithmeticException {
		BigDecimal bd1, bd2;
		bd1 = new BigDecimal("1");
		bd2 = new BigDecimal(getOperand1());
		result = bd1.divide(bd2, 4, RoundingMode.HALF_UP).toPlainString();
		setFlags(result);
		return result;
	}
	public String power (String a) {
		BigDecimal bd1;
		bd1 = new BigDecimal(getOperand1());
		result = bd1.pow(Integer.parseInt(getOperand2())).toPlainString();
		setFlags(result);
		return result;
	}
	
	public String sqrt (String a) {
		result = "0";
		return result;
	}
	
	public String rate (String a) {
		BigDecimal bd1, bd2;
		bd1 = new BigDecimal(getOperand1());
		bd2 = new BigDecimal(getOperand2());
		result = bd2.divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP).multiply(bd1).toPlainString();
		setFlags(result);
		return result;
	}
	
	/////////insertion
	public CState handleInput(String in) {
		//lambda here or in other class or as members??
		///------------run here
	inputType = parseToEnum(in);
	return doCalculation(in);
	}
	
	private CInput inputType;
	CInput parseToEnum(String in){
		if (in != null && in.matches("\\d+")){
			result = in;
			inputType = CInput.num;
			return inputType;
		}
		
		if(in.equals(".")) {
			inputType = CInput.point;
			return inputType;
		}
		if(in.equals("del")) {
			inputType = CInput.del;
			return inputType;
		}
		
		if(in.equals("AC")) {
			inputType = CInput.AC;
			return inputType;
		}
		if(in.equals("sign")) {
			inputType = CInput.sign;
			return inputType;
		}
		
		if(in.equals("enter")) {
			inputType = CInput.enter;
			return inputType;
		}
		//-----
		if(in.equals("add")) {
			currentF = this::add;
			inputType = CInput.func2op;
			return inputType;
		}
		
		if(in.equals("sub")) {
			currentF = this::sub;
			inputType = CInput.func2op;
			return inputType;
		}
		
		if(in.equals("mul")) {
			currentF = this::multiply;
			inputType = CInput.func2op;
			return inputType;
		}
		if(in.equals("div")) {
			currentF = this::divide;
			inputType = CInput.func2op;
			return inputType;
		}

		if(in.equals("mod")) {
			currentF = this::mod;
			inputType = CInput.func2op;
			return inputType;
		}
		
		if(in.equals("reverse")) {
			currentF = this::reverse;
			inputType = CInput.func1op;
			return inputType;
		}

		if(in.equals("power")) {
			currentF = this::power;
			inputType = CInput.func2op;
			return inputType;
		}
		if(in.equals("sqrt")) {
			currentF = this::sqrt;
			inputType = CInput.func1op;
			return inputType;
		}
		if(in.equals("rate")) {
			currentF = this::rate;
			inputType = CInput.func2op;
		}
		return inputType;
	}
	
	private CState currentState = CState.AC;
	EnumMap<CState, EnumMap> stateMap;
	CState doCalculation(String in) {
		Function <String, CState> f = (Function<String, CState>)(stateMap.get(currentState).get(inputType));
		currentState = f.apply(in);
		//hint = "state: " + currentState +" in= " + inputType + " op1= "+getOperand1()+", op2= "+getOperand2();
		//hint = "state: " + currentState +" op1: " + getOperand1()+ ", " + in;
		String operation; 
		if(inputType == CInput.func2op && currentState == CState.Func)
			operation = in;
		operation ="";
		hint = "op1: " + getOperand1();
		return currentState;
	}
	
	///Transition from one state to another
	
	private CState reset (String a) {
		resetFlags();
		setOperand1(new StringBuilder());
		setOperand2(new StringBuilder());
		currentF = null;//notning 
		result = a.equals("AC")?"0":a;//"0";
		return CState.AC;//state AC 
	}
	
	private String doNothing (String a) {
		//that func instead flag of new operation or current
		//to reduce flag checking around
		//maybe swap here operand?
		return result;
	}

//private Function getCurrentFunc(){ return currentF; }
	
///////////////Lambda
//----------------------------------------------AC
/*			
CState.OP1; CState.AC; CState.Func;CState.OP2;
*/				
//append op1
Function<String, CState>  num_op1 = (String a)-> {
	setOperand1(new StringBuilder());//if remove reduce funcs in op1
	setOperand1(a);
	result = getOperand1();
	return CState.OP1;
};

//add point to op1
Function<String, CState> point_op1 = (String a)-> { 
	setOperand1("0" + a.toString());
	pointFlag = true;
	result = getOperand1();
	return CState.OP1;
};				
//del in AC 
Function<String, CState> del_ac = (String a)-> CState.AC;
//AC in AC
Function<String, CState> ac_ac = (String a) -> reset("0");
//add sign in AC
Function<String, CState> sign_op1 = (String a)-> { 
	setOperand1("-");
	signFlag = true;
	result = getOperand1();
	return CState.OP1;
};
//enter
Function<String, CState> enter_ac = (String a)-> CState.AC;
//func
Function<String, CState> func_ac = (String a)-> CState.AC;				


//----------------------------------------------OP1
//num1_op1 point1_op1 del_op1 sign1_op1 enter_op1 ifunc_func
//append op1
Function<String, CState> num1_op1 = (String a)-> {
	setOperand1(a);
	result = getOperand1();
	return CState.OP1;
};
//add point to op1
Function<String, CState> point1_op1 = (String a)-> { 
	if (pointFlag == false){
	setOperand1(a.toString());
	pointFlag = true;
	}
	result = getOperand1();
	return CState.OP1;
};
//del in OP1 
Function<String, CState> del_op1 = (String a)-> { 
	int l = getOperandBuilder1().length();
	if (l != 0)
	getOperandBuilder1().setLength(l-1);
	if(getOperandBuilder1().length() == 0)
	setOperand1("0");
	
	result = getOperand1();
	setFlags(result);
	return CState.OP1;
};

//add sign in OP1
Function<String, CState> sign1_op1 = (String a)-> {
	if(signFlag == false){
	getOperandBuilder1().insert(0, "-");
	signFlag = true;
	}else {
	getOperandBuilder1().delete(0, 1);
	signFlag = false;
	}
	result = getOperand1();
	return CState.OP1;
};
//enter
Function<String, CState> enter_op1 = (String a)-> { return CState.OP1;	};
//add
Function<String, CState> func_func = (String a)-> {
	result = "";
	signFlag = false;
	pointFlag = false;
	//currentF = getCurrentFunc( );
	return CState.Func;
};

//one operand func
Function<String, CState> ifunc_func = (String a)-> {
	result = "";
	signFlag = false;
	pointFlag = false;
try{
	result = currentF.apply("0");
	}catch(ArithmeticException ex){
		result = "ERROR";
		return CState.AC;
		}
	setOperand1(new StringBuilder());
	setOperand2(new StringBuilder());
	operand1.append(result);
	setFlags(result);
	return CState.Func;
};						

//---------------------------------------------Func
//num_op2 point_op2 del2_ac sign2_op1 enter_func func2_func
//append op2
Function<String, CState> num_op2 = (String a)-> {
	setOperand2(a);
	result = getOperand2();
	return CState.OP2;
};							
//add point to op2
Function<String, CState> point_op2 = (String a)-> {
	setOperand2("0" + a.toString());
	pointFlag = true;
	result = getOperand2();
	return CState.OP2;
};
						
//del in F 
Function<String, CState> del2_ac = (String a)-> reset("");
//add sign in OP1						
Function<String, CState> sign2_op1 = (String a)-> {
	setFlags(getOperand1());// if remove remains only sign_op1!!!
	if(signFlag == false){
		getOperandBuilder1().insert(0, "-");
		signFlag = true;
	}else {
		getOperandBuilder1().delete(0, 1);
		signFlag = false;
	}
	result = getOperand1();
	return CState.OP1;
};
//enter
Function<String, CState> enter_func = (String a)-> { 
	currentF = this::doNothing;
	result = getOperand1();
return CState.Func;
};
//func
Function<String, CState> func2_func = (String a)-> CState.Func;					
		

//---------------------------------------------OP2
//num2_op2 point2_op2 del_op2 sign_op2	enter2_func	func3_func					
//append op2
Function<String, CState> num2_op2 = (String a)-> { 
	setOperand2(a);
	result = getOperand2();
	return CState.OP2;
};

//add point to op2
Function<String, CState> point2_op2 = (String a)-> { 
	if (pointFlag == false){
		if(getNumberQnt(getOperandBuilder2()) < 12) {
			setOperand2(a.toString());
			pointFlag = true;
		}
	}
	result = getOperand2();
	return CState.OP2;
};
//del in OP2 
Function<String, CState> del_op2 = (String a)-> { 
	int l = getOperandBuilder2().length();
	if (l != 0)
		getOperandBuilder2().setLength(l-1);
	result = getOperand2();
	return CState.OP2;
};
					
//add sign in OP2
Function<String, CState> sign_op2 = (String a)-> {
	if(signFlag == false){
		getOperandBuilder2().insert(0, "-");
		signFlag = true;
	}else {
		getOperandBuilder2().delete(0, 1);
		signFlag = false;
	}
	result = getOperand2();
	return CState.OP2;
};
//enter
Function<String, CState> enter2_func = (String a)-> {
	try{
		result = currentF.apply("");
		}catch(ArithmeticException ex){
			return reset("ERROR");
			//return 0;//Maybe should add a state: exception
		}
	currentF = this::doNothing;
	setOperand1(new StringBuilder());
	setOperand2(new StringBuilder());
	operand1.append(result);
	resetFlags();
	return CState.Func;
};
//func
Function<String, CState> func3_func = (String a)-> { 
	try{
		result = currentF.apply("");
	}catch(ArithmeticException ex) {
		return reset("ERROR");//will return 0, to switch state;
	}
	setOperand1(new StringBuilder());
	setOperand2(new StringBuilder());
	operand1.append(result);
	return CState.Func;//state OP1 or F?
};



public CalcState() {
	this.stateMap = new EnumMap<CState, EnumMap>(CState.class);
	EnumMap<CInput, Function<String, CState> > inputToAC = 
			new EnumMap<CInput, Function<String, CState>>(CInput.class);
	//num, point, del, AC, sign, enter, func2op(add, sub, mul,div,mod, reverse,power,sqrt, rate)
	//AC
	inputToAC.put(CInput.num, num_op1);
	inputToAC.put(CInput.point, point_op1);
	inputToAC.put(CInput.del, del_ac);
	inputToAC.put(CInput.AC, ac_ac);
	inputToAC.put(CInput.sign, sign_op1);
	inputToAC.put(CInput.enter, enter_ac);
	inputToAC.put(CInput.func2op, func_ac);
	inputToAC.put(CInput.func1op, func_ac);
	//OP!
	EnumMap<CInput, Function<String, CState> > inputToOP1 = 
			new EnumMap<CInput, Function<String, CState>>(CInput.class);
	//num, point, del, AC, sign, enter, func2op(add, sub, mul,div,mod, reverse,power,sqrt, rate)
	//OP1
	//num1_op1 point1_op1 del_op1 sign1_op1 enter_op1 func_func ifunc_func
	inputToOP1.put(CInput.num, num1_op1);
	inputToOP1.put(CInput.point, point1_op1);
	inputToOP1.put(CInput.del, del_op1);
	inputToOP1.put(CInput.AC, this::reset);
	inputToOP1.put(CInput.sign, sign1_op1);
	inputToOP1.put(CInput.enter, enter_op1);
	inputToOP1.put(CInput.func2op, func_func);
	inputToOP1.put(CInput.func1op, ifunc_func);
	//Func
	EnumMap<CInput, Function<String, CState> > inputToFunc = 
			new EnumMap<CInput, Function<String, CState>>(CInput.class);
	//num, point, del, AC, sign, enter, func2op(add, sub, mul,div,mod, reverse,power,sqrt, rate)
	//Func
	//num_op2 point_op2 del2_ac sign2_op1 enter_func func2_func
	inputToFunc.put(CInput.num, num_op2);
	inputToFunc.put(CInput.point, point_op2);
	inputToFunc.put(CInput.del,del2_ac);
	inputToFunc.put(CInput.AC, this::reset);
	inputToFunc.put(CInput.sign, sign2_op1);
	inputToFunc.put(CInput.enter, enter_func);
	inputToFunc.put(CInput.func2op, func2_func);
	inputToFunc.put(CInput.func1op, ifunc_func);
	
	EnumMap<CInput, Function<String, CState> > inputToOP2 = 
			new EnumMap<CInput, Function<String, CState>>(CInput.class);
	//num, point, del, AC, sign, enter, func2op(add, sub, mul,div,mod, reverse,power,sqrt, rate)
	//OP2
	//num2_op2 point2_op2 del_op2 sign_op2	enter2_func	func3_func
	inputToOP2.put(CInput.num, num2_op2);
	inputToOP2.put(CInput.point, point2_op2);
	inputToOP2.put(CInput.del, del_op2);
	inputToOP2.put(CInput.AC, this::reset);
	inputToOP2.put(CInput.sign, sign_op2);
	inputToOP2.put(CInput.enter, enter2_func);
	inputToOP2.put(CInput.func2op, func3_func);
	inputToOP2.put(CInput.func1op, func3_func);
	
	stateMap.put(CState.AC, inputToAC);
	stateMap.put(CState.OP1, inputToOP1);
	stateMap.put(CState.Func, inputToFunc);
	stateMap.put(CState.OP2, inputToOP2);
}
}

 enum CState {
	AC(0),OP1(1),Func(2),OP2(3);
	CState(int index){this.index = index;}
	private int index;
	}
 
enum CInput {
	num, point, del, AC, sign, enter, func2op, func1op, add, sub, mul,div,mod, reverse,power,sqrt, rate
}

