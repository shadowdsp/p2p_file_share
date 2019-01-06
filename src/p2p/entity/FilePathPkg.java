package p2p.entity;

import p2p.utils.StringUtils;
import p2p.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shadowdsp on 2019/1/5.
 */

public class FilePathPkg {

    private List<String> filePathList;
    private Integer tag;

    public FilePathPkg(Integer tag, List<String> filePathList) {
        this.tag = tag;
        this.filePathList = filePathList;
    }

    public static String encode(FilePathPkg pkg) {
        Integer tag = pkg.getTag();
        List<String> files = pkg.getFilePathList();
        StringBuilder sb = new StringBuilder(tag + "@");
        for (String file : files) {
            sb.append(file).append(Utils.FILEPATH_SEPARATOR);
        }
        return sb.toString();
    }

    public static FilePathPkg decode(String info) {
        List<String> arrayList = new ArrayList<>();
        String[] tmps = info.split(Utils.PATHTAG_SEPARATOR);
        if (tmps.length > 1 && !StringUtils.isEmpty(tmps[1])) {
            String[] strs = tmps[1].split(Utils.FILEPATH_SEPARATOR);
            for (String str : strs) {
                if (!StringUtils.isEmpty(str)) {
                    arrayList.add(str);
                }
            }
        }
        return new FilePathPkg(Integer.parseInt(tmps[0]), arrayList);
    }

    public List<String> getFilePathList() {
        return filePathList;
    }

    public void setFilePathList(List<String> filePathList) {
        this.filePathList = filePathList;
    }

    public Integer getTag() {
        return tag;
    }

    public void setTag(Integer tag) {
        this.tag = tag;
    }
}
