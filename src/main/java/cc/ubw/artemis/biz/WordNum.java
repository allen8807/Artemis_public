package cc.ubw.artemis.biz;

/**
 * Created with IntelliJ IDEA.
 * User: Allen
 * Date: 2020/5/21 0021
 * Time: 15:07
 * To change this template use File | Settings | File Templates.
 * Description:
 */
public class WordNum implements Comparable<WordNum> {
    private String word;
    private Long sum;

    public WordNum(String word, Long sum) {
        this.word = word;
        this.sum = sum;
    }

    @Override
    public int compareTo(WordNum wordNum) {
        int res = 1;
        if (wordNum != null) {
            res = this.getSum().compareTo(wordNum.getSum());
            if (res == 0) {
                res = this.getWord().compareTo(wordNum.getWord());
            }
        }
        return res;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Long getSum() {
        return sum;
    }

    public void setSum(Long sum) {
        this.sum = sum;
    }
}
