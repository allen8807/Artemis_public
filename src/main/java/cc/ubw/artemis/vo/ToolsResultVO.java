package cc.ubw.artemis.vo;

/**
 * 小工具的输出类
 */
public class ToolsResultVO {
    private String result;

    private String retryUrl;
    private String retrySlogan;
    /*用于下载的json数据*/
    private String downloadName;
    private String downloadJson;
    private String downloadUrl;
    private String downloadSlogan;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getRetryUrl() {
        return retryUrl;
    }

    public void setRetryUrl(String retryUrl) {
        this.retryUrl = retryUrl;
    }

    public String getRetrySlogan() {
        return retrySlogan;
    }

    public void setRetrySlogan(String retrySlogan) {
        this.retrySlogan = retrySlogan;
    }


    public String getDownloadJson() {
        return downloadJson;
    }

    public void setDownloadJson(String downloadJson) {
        this.downloadJson = downloadJson;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getDownloadSlogan() {
        return downloadSlogan;
    }

    public void setDownloadSlogan(String downloadSlogan) {
        this.downloadSlogan = downloadSlogan;
    }

    public String getDownloadName() {
        return downloadName;
    }

    public void setDownloadName(String downloadName) {
        this.downloadName = downloadName;
    }
}
