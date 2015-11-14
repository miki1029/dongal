package com.dongal.api.config;

import org.hibernate.dialect.MySQL5InnoDBDialect;

import java.sql.Types;

/**
 * @author Freddi
 */
public class Mysql5BitBooleanDialect extends MySQL5InnoDBDialect {
    public Mysql5BitBooleanDialect() {
        super();
        registerColumnType( Types.BOOLEAN, "bit" );
//        registerColumnType( Types.TIMESTAMP, "datetime($l)" );
    }
}
