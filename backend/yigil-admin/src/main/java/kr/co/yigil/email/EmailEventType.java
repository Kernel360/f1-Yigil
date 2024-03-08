package kr.co.yigil.email;

public enum EmailEventType {
    ADMIN_SIGN_UP_ACCEPT {
        @Override
        public String toString() {
            return "accept";
        }
    },
    ADMIN_SIGN_UP_REJECT {
        @Override
        public String toString() {
            return "reject";
        }
    }
}
