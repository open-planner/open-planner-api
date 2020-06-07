package br.edu.ifpb.mestrado.openplanner.api.application.service.exception;

public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 4026908748344431668L;

    private String[] args;

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, String... args) {
        super(message);
        this.args = args;
    }

    public String[] getArgs() {
        return args;
    }

}
