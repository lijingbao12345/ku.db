package dbutils;

import com.example.gaojichonci01_3.bean.DatasBean;
import com.example.gaojichonci01_3.db.DatasBeanDao;

import java.util.List;

public class DbUtils {
    public static void insert(DatasBean datasBean) {
        DatasBean datasBean1 = itemQuery(datasBean);
        if (datasBean1 == null) {
            MyApp.daoSession.insert(datasBean);
        }
    }

    public static void delete(DatasBean datasBean) {
        DatasBean datasBean1 = itemQuery(datasBean);
        if (datasBean1 != null) {
            MyApp.daoSession.delete(datasBean);
        }
    }

    public static void update(DatasBean datasBean) {
        DatasBean datasBean1 = itemQuery(datasBean);
        if (datasBean1 != null) {
            MyApp.daoSession.update(datasBean);
        }
    }

    public static List<DatasBean> queryAll(){
        return MyApp.daoSession.loadAll(DatasBean.class);
    }


    public static DatasBean itemQuery(DatasBean datasBean) {
        return MyApp.daoSession.queryBuilder(DatasBean.class).where(DatasBeanDao.Properties.
                Llid.eq(datasBean.getLlid()))
                .unique();
    }
}
