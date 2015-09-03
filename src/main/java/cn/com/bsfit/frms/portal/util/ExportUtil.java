package cn.com.bsfit.frms.portal.util;

import cn.com.bsfit.frms.portal.base.util.ExportExcel;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 导出Excel的工具类
 * Created by 崇建 on 2015-7-29.
 */
public class ExportUtil {

    /**
     * @param response      HttpServletResponse
     * @param list          需要导出的数据
     * @param headersConfig excel表头配置。例如:
     *                      Map<String, String> map = new LinkedHashMap<String, String>();
     *                      map.put("ruleId", "规则名");
     *                      map.put("ruleName", "规则内容");
     *                      map.put("score", "规则分值");
     *                      map.put("riskNumber", "统计数目");
     *                      map.put("riskProportion", "所占比例");
     * @param fileName      下载的文件的文件名
     * @throws IOException
     */
    @SuppressWarnings("rawtypes")
    public static void export(HttpServletResponse response, List list, LinkedHashMap<String, String> headersConfig, String fileName) throws IOException {
        response.setHeader("Content-Type", "application/vnd.ms.excel;charset=GBK");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(StringUtils.isEmpty(fileName) ? "列表" : fileName + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".xls", "UTF-8") + "\"");

        OutputStream bos = new BufferedOutputStream(response.getOutputStream());
        ExportExcel excel = new ExportExcel();
        excel.addHeader(headersConfig.values().toArray(new String[headersConfig.size()]));
        Object[] values = new Object[headersConfig.size()];
        if (!CollectionUtils.isEmpty(list)) {
            // 如果List中存储的不是Map，将其转换成Map
            if (!(list.get(0) instanceof Map)) {
                list = (List) JSON.toJSON(list);
            }
            // 遍历行
            for (Object obj : list) {
                int i = 0;
                Map map = (Map) obj;
                //遍历列
                for (String key : headersConfig.keySet()) {
                    values[i++] = map.get(key);
                }
                excel.addRow(values);
            }
        }
        excel.write(bos);
        bos.flush();
        bos.close();
    }
}
