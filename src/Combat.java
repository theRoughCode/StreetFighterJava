public class Combat {
    boolean LP = false;
    boolean MP = false;
    boolean HP = false;
    boolean LK = false;
    boolean MK = false;
    boolean HK = false;
    int dmg = 0;

    void attack() {
        this.dmg = this.LP ? 5 : (this.MP ? 8 : (this.HP ? 10 : (this.LK ? 5 : (this.MK ? 8 : (this.HK ? 12 : 0)))));
    }

    void lightPunch() {
        if (!this.checkFalse()) {
            this.LP = true;
        }
    }

    void midPunch() {
        if (!this.checkFalse()) {
            this.MP = true;
        }
    }

    void hardPunch() {
        if (!this.checkFalse()) {
            this.HP = true;
        }
    }

    void lightKick() {
        if (!this.checkFalse()) {
            this.LK = true;
        }
    }

    void midKick() {
        if (!this.checkFalse()) {
            this.MK = true;
        }
    }

    void hardKick() {
        if (!this.checkFalse()) {
            this.HK = true;
        }
    }

    boolean checkFalse() {
        if (this.LP || this.MP || this.HP || this.LK || this.MK || this.HK) {
            return true;
        }
        return false;
    }
}