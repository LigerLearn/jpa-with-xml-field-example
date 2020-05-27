package com.ligerlearn.example.jpa.hibernate_types;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * Store and retrieve a PostgreSQL "xml" column as a Java string.
 *
 * Adapted from https://wiki.postgresql.org/wiki/Hibernate_XML_Type (Jan 2020).
 */
public class SQLXMLType implements org.hibernate.usertype.UserType {

    /**
     * Fully qualified class name, so it can be referenced within annotations.
     * It is a final string because it must be a constant.
     */
    public static final String FULLY_QUALIFIED_CLASS_NAME = "com.ligerlearn.example.jpa.hibernate_types.SQLXMLType";

    private final int[] sqlTypesSupported = new int[] { Types.VARCHAR };

    @Override
    public int[] sqlTypes() {
        return sqlTypesSupported;
    }

    @Override
    public Class returnedClass() {
        return String.class;
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        if (x == null) {
            return y == null;
        } else {
            return x.equals(y);
        }
    }

    @Override
    public int hashCode(Object x) throws HibernateException {
        return x == null ? null : x.hashCode();
    }

    /**
     * Retrieve an instance of the mapped class from a JDBC resultset. Implementors
     * should handle possibility of null values.
     *
     * @param rs      a JDBC result set
     * @param names   the column names
     * @param session
     * @param owner   the containing entity  @return Object
     * @throws HibernateException
     * @throws SQLException
     */
    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner) throws HibernateException, SQLException {
        assert(names.length == 1);
        String xmldoc = rs.getString( names[0] );
        return rs.wasNull() ? null : xmldoc;
    }

    /**
     * Write an instance of the mapped class to a prepared statement. Implementors
     * should handle possibility of null values. A multi-column type should be written
     * to parameters starting from <tt>index</tt>.
     *
     * @param st      a JDBC prepared statement
     * @param value   the object to write
     * @param index   statement parameter index
     * @param session
     * @throws HibernateException
     * @throws SQLException
     */
    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session) throws HibernateException, SQLException {
        if (value == null) {
            st.setNull(index, Types.OTHER);
        } else {
            st.setObject(index, value, Types.OTHER);
        }
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        if (value == null)
            return null;
        return new String( (String)value );
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        return (String) value;
    }

    @Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return (String) cached;
    }

    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return original;
    }
}

