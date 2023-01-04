package org.joget.marketplace;

import org.joget.apps.app.service.AppPluginUtil;
import org.joget.apps.app.service.AppUtil;
import org.joget.apps.datalist.model.DataList;
import org.joget.apps.datalist.model.DataListColumn;
import org.joget.apps.datalist.model.DataListColumnFormatDefault;
import org.joget.commons.util.StringUtil;



public class DatalistNumberFormatter extends DataListColumnFormatDefault{
        private final static String MESSAGE_PATH = "messages/DatalistNumberFormatter";

    @Override
    public String getName() {
        return "Datalist Number Formatter";
    }

    @Override
    public String getVersion() {
        return "8.0.0";
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
       String result ="";

     result = StringUtil.numberFormat(num, getPropertyString("style"), getPropertyString("prefix"), getPropertyString("postfix"), "true".equalsIgnoreCase(getPropertyString("useThousandSeparator")), getPropertyString("numOfDecimal"));
            
            
               

    return result;
}
    @Override
    public String getPropertyOptions() {
return AppUtil.readPluginResource(getClass().getName(), "/properties/DatalistNumberFormatter.json", null, true, MESSAGE_PATH);    }
    
}

