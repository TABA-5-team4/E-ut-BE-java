package taba.team4.eut.common.utils;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class YnBooleanTypeHandler extends BaseTypeHandler<Boolean> {

    public static Boolean convertStringToBoolean(String stringValue) {
        return (stringValue == null)? null: stringValue.equalsIgnoreCase("Y");
    }

    public static String convertBooleanToString(Boolean booleanValue) {
        return (booleanValue == null)? null: (booleanValue ? "Y": "N");
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Boolean parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, convertBooleanToString(parameter));
    }

    @Override
    public Boolean getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return convertStringToBoolean(rs.getString(columnName));
    }

    @Override
    public Boolean getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return convertStringToBoolean(rs.getString(columnIndex));
    }

    @Override
    public Boolean getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return convertStringToBoolean(cs.getString(columnIndex));
    }
}
