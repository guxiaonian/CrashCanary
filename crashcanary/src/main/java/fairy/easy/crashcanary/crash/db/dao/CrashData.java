package fairy.easy.crashcanary.crash.db.dao;


public class CrashData {

    public static final String DATABASE_NAME = "crashCanary.db";

    public static final int DATABASE_VERSION = 1;


    public static final class TABLE_PERSON {
        public static final String TABLE_NAME = "CrashCanary";
        public static final String _ID = "_ID";
        public static final String _START_TIME = "startTime";
        public static final String _DETAILED_ERROR = "detailedError";
        public static final String _NORMAL_ERROR = "normalError";

    }
}
