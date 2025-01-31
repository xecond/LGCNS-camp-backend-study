package ex01.assembler;

import ex01.ChangePasswordService;
import ex01.MemberDAO;
import ex01.MemberRegisterService;

public class Assembler {
	private MemberDAO memberDAO;
	private MemberRegisterService regSvc;
	private ChangePasswordService pwdSvc;
	
	public Assembler() {
		this.memberDAO = new MemberDAO();
		this.regSvc = new MemberRegisterService(this.memberDAO);
		this.pwdSvc = new ChangePasswordService(this.memberDAO);
	}
	
	public MemberDAO getMemberDAO() {
		return this.memberDAO;
	}
	
	public MemberRegisterService getRegSvc() {
        return this.regSvc;
    }
    
    public ChangePasswordService getPwdSvc() {
        return this.pwdSvc;
    }

}
