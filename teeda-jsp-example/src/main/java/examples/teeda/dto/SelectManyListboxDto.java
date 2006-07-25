/**
 *
 */
package examples.teeda.dto;

/**
 * @author shot
 */
public class SelectManyListboxDto {

    private String[] aaa = { "1" };

    public String[] getAaa() {
        return aaa;
    }

    public String getBbb() {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < aaa.length; i++) {
            buf.append(aaa[i]);
        }
        return buf.toString();
    }

    public void setAaa(String[] aaa) {
        this.aaa = aaa;
    }

}
