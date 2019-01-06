package p2p.entity;
import p2p.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shadowdsp on 2019/1/5.
 */

public class FilePathPkg {

    private static String PACK_SEPARATOR = ";";

    public static String encode(List<String> files) {
        StringBuilder sb = new StringBuilder();
        for (String file: files) {
            sb.append(file).append(PACK_SEPARATOR);
        }
        return sb.toString();
    }

    public static List<String> decode(String info) {
        List<String> arrayList = new ArrayList<>();
        String[] strs = info.split(PACK_SEPARATOR);
        for (String str: strs) {
            if (!StringUtils.isEmpty(str)) {
                arrayList.add(str);
            }
        }
        return arrayList;
    }

}
