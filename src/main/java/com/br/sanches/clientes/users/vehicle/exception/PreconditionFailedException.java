package com.br.sanches.clientes.users.vehicle.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public class PreconditionFailedException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public PreconditionFailedException(String message) {
		super(message, null, true, false);
	}
}

