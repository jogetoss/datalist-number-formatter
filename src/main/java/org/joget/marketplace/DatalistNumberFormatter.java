package org.joget.marketplace;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.joget.apps.app.service.AppPluginUtil;
import org.joget.apps.app.service.AppUtil;
import org.joget.apps.datalist.model.DataList;
import org.joget.apps.datalist.model.DataListColumn;
import org.joget.apps.datalist.model.DataListColumnFormatDefault;
import org.joget.apps.datalist.service.DataListService;
import org.joget.commons.util.StringUtil;
import org.joget.commons.util.TimeZoneUtil;

public class DatalistNumberFormatter extends DataListColumnFormatDefault{
    private final static String MESSAGE_PATH = "messages/DatalistNumberFormatter";

    @Override
    public String getName() {
        return "Datalist Number Formatter";
    }

    @Override
    public String getVersion() {
        return "8.0.1";
    }

    @Override
    public String getDescription() {
        return AppPluginUtil.getMessage("org.joget.marketplace.DatalistNumberFormatter.pluginDesc", getClassName(), MESSAGE_PATH);
    }

    @Override
    public String getLabel() {
        return AppPluginUtil.getMessage("org.joget.marketplace.DatalistNumberFormatter.pluginLabel", getClassName(), MESSAGE_PATH);
    }

    @Override
    public String getClassName() {
        return getClass().getName();
    }
        
    @Override
    public String format(DataList dataList, DataListColumn column, Object row, Object value) {
        String num = (String) value;
        String result = "";
        result = StringUtil.numberFormat(getValueFromRow(num, row), getPropertyString("style"), getValueFromRow(getPropertyString("prefix"), row), getValueFromRow(getPropertyString("postfix"), row), "true".equalsIgnoreCase(getPropertyString("useThousandSeparator")), getPropertyString("numOfDecimal"));

        return result;
   }
   
    @Override
    public String getPropertyOptions() {
        return AppUtil.readPluginResource(getClass().getName(), "/properties/DatalistNumberFormatter.json", null, true, MESSAGE_PATH);    
    }
    
    protected String getValueFromRow(String expr, Object row) {
        Pattern pattern = Pattern.compile("\\{([a-zA-Z0-9_]+)\\}");
        Matcher matcher = pattern.matcher(expr);
        
        while (matcher.find()) {
            String key = matcher.group(1);
            Object value = evaluate(row, key);
            if (value != null) {
                expr = expr.replaceAll(StringUtil.escapeRegex("{"+key+"}"), value.toString());
            }
            //try again with c_
            if(value == null && !key.startsWith("c_")){
                value = evaluate(row, "c_" + key);
                if (value != null) {
                    expr = expr.replaceAll(StringUtil.escapeRegex("{"+key+"}"), value.toString());
                }
            }
        }
        
        return expr;
    }
    
    protected Object evaluate(Object row, String propertyName) {
        if (propertyName != null && !propertyName.isEmpty()) {
            try {
                Object value = DataListService.evaluateColumnValueFromRow(row, propertyName); 
                //handle for lowercase propertyName
                if (value == null) {
                    value = DataListService.evaluateColumnValueFromRow(row, propertyName.toLowerCase());
                }
                return value;
            } catch (Exception e) {}
        }
        return null;
    }
}
