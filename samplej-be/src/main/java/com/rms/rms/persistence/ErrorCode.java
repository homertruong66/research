package com.rms.rms.persistence;

/**
 * homertruong
 */

public enum ErrorCode {
	DUPLICATE_ENTITY
	,ENTITY_NOT_FOUND
	,INVALID_PARAM
	,UNSUPPORTED_PARAM
	;
	
	public String format(String format, Object... args){
		return this.name()+" " +String.format(format, args);
	}
	
	public String param(String param,Object value){
		return format("(`%s` : `%s`)",param,value);
	}
	
	public String value(Object value){
		return format("(`%s`)",value);
	}
}
