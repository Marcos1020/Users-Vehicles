/*
* Copyright 2019 Hapvida
*************************************************************
*Name    : ConverterUtil.java
*Date    : 19/05/2019
*System  : Next Digital Platform
*************************************************************
*/
package com.br.sanches.clientes.users.vehicle.utils;

import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;

@Slf4j
public class ConverterUtil {
  public static final String FORMATO_DATA = "yyyy-MM-dd HH:mm:ss";
  public static Timestamp nowTime() {
    return new Timestamp( System.currentTimeMillis() );
  }

}