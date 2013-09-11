import com.dd.plist.NSDictionary;
import com.dd.plist.PropertyListParser;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: chris
 * Date: 13-8-16
 * Time: 下午6:23
 * To change this template use File | Settings | File Templates.
 */
public class Main {

    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {

        if(args.length==0)
        {
            System.out.println("USAGE: java -jar fnt.jar <input.plist> ");
            System.exit(0);
        }
        try {
            File file = new File(args[0]);
            NSDictionary rootDict = (NSDictionary) PropertyListParser.parse(file);
            Object obj=rootDict.objectForKey("frames");
            NSDictionary dir= (NSDictionary) obj;
            NSDictionary meta= (NSDictionary) rootDict.objectForKey("metadata");
            String fileName=meta.objectForKey("realTextureFileName").toString();

            System.out.println("info face=\"Font\" size=9 bold=0 italic=0 charset=\"\" unicode=0 stretchH=256 smooth=0 aa=0 padding=0,0,0,0 spacing=1,1\n" +
                    "common lineHeight=9 base=9 scaleW=256 scaleH=256 pages=1 packed=0\n" +
                    "page id=0 file=\""+fileName+"\"\n" +
                    "chars count="+dir.keySet().size());

            for(String k :dir.keySet()){
                String key=k;
                   NSDictionary val= (NSDictionary) dir.objectForKey(key);
                String offset=val.objectForKey("offset").toString();
                String frame=val.objectForKey("frame").toString();
                frame=frame.replaceAll("\\{","").replaceAll("\\}","");
                offset=offset.replaceAll("\\{","").replaceAll("\\}","");

                String[] f=frame.split(",");
                String[] o=offset.split(",");
                int x=Integer.parseInt(f[0]);
                int y=Integer.parseInt(f[1]);
                int w=Integer.parseInt(f[2]);
                int h=Integer.parseInt(f[3]);
                        Character c;

                int ox=Integer.parseInt(o[0]);
                int oy=Integer.parseInt(o[1]);

                System.out.println(String.format("char id=%d x=%d y=%d width=%d height=%d xoffset=%d yoffset=%d xadvance=%d page=0 chnl=0",
                        (int)key.charAt(0),x,y,w,h,0,0,w));

            }
            System.out.println("kernings count=-1");

            return;
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
