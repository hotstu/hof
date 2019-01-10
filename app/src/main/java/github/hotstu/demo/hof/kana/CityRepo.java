package github.hotstu.demo.hof.kana;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author hglf <a href="https://github.com/hotstu">hglf</a>
 * @desc
 * @since 2019/1/10
 */
public class CityRepo {

    private final Map map;

    private CityRepo(Context ctx) {
        Context context = ctx.getApplicationContext();
        InputStream open = null;
        try {
            open = context.getAssets().open("city.json");
            Gson g = new GsonBuilder().create();
            map = g.fromJson(new InputStreamReader(open), Map.class);
        } catch (IOException e) {
            throw new RuntimeException();
        } finally {
            if (open != null) {
                try {
                    open.close();
                } catch (Exception e) {
                }
            }
        }
    }

    public Map getMap() {
        return map;
    }

    public List<CityNode> obtain(CityNode parent) {
        List<CityNode> ret = new ArrayList<>();
        List list = null;
        int deep = parent.getDeep();
        Map map = parent.getRaw();
        if (deep == 0) {
            list = (List) map.get("province");
        }
        if (deep == 1) {
            list = (List) map.get("city");

        }
        if (deep == 2) {
            Object county = map.get("county");
            if (county instanceof Map) {
                list = new ArrayList();
                list.add(county);
            } else {
                list = (List) county;
            }
        }

        if (list != null) {
            for (Object o : list) {
                Map item = ((Map) o);
                String name = (String) item.get("name");
                ret.add(new CityNode(parent, name, deep + 1, item));
            }
        }

        return ret;

    }

    private static CityRepo sInstance;

    public static CityRepo getInstance(Context ctx) {
        if (sInstance == null) {
            synchronized (CityRepo.class) {
                if (sInstance == null) {
                    sInstance = new CityRepo(ctx);
                }
            }
        }
        return sInstance;
    }
}
