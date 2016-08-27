public class Movement {
    int x = 10;
    int y = 160;
    int dx;
    int dy;
    int dxstore = 1;
    int maxJumpHeight = 50;
    int minJumpHeight = 160;
    int jumpStartPos;
    int userDir = 1;
    private int lastPos;
    int userAniWidth = 0;
    int userAniHeight = 0;
    int stop = 1;
    int maxX;
    long jumpLastFrameTime = 0;
    long walkLastFrameTime = 0;
    double xspeed = 2.0;
    double yspeed = 4.0;
    int xdelay = 10;
    boolean jumpDone;
    boolean jumpMax;
    boolean jumpR = false;
    boolean jumpL = false;
    int dmgLvl = 0;
    int hitLvl = 0;
    Thread jumpAnimation;
    Thread punchAnimation;
    Combat attack = new Combat();

    void move() {
        if (!this.attack.checkFalse()) {
            this.checkWalls();
            if (this.dx != 0) {
                this.walk(this.dx, this.userDir, this.xspeed, this.xdelay);
            }
        }
    }

    void walk(int dx, int dir, double speed, int timeDelay) {
        if (System.currentTimeMillis() - this.walkLastFrameTime > (long)timeDelay) {
            this.x = (int)((double)this.x + (double)(dx * dir) * speed);
            this.walkLastFrameTime = System.currentTimeMillis();
        }
    }

    void setLastPos() {
        this.lastPos = this.x;
    }

    void startMoveJump() {
        this.jumpStartPos = this.x;
        this.jumpLastFrameTime = System.currentTimeMillis();
    }

    boolean hitMove(int hitLvl, boolean attacker) {
        if (!attacker) {
            this.hitLvl = hitLvl;
        }
        if (hitLvl == 1) {
            if (Math.abs(this.lastPos - this.x) > 30) {
                return false;
            }
            this.x -= this.userDir * 2;
            return true;
        }
        if (hitLvl == 2) {
            if (Math.abs(this.lastPos - this.x) > 40) {
                return false;
            }
            this.x -= this.userDir * 2;
            return true;
        }
        if (hitLvl == 3) {
            if (Math.abs(this.lastPos - this.x) > 50) {
                return false;
            }
            this.x -= this.userDir * 2;
            return true;
        }
        return false;
    }

    void move(int dir) {
        if (dir == -1 && this.x <= 0) {
            dir = 0;
            this.x = 0;
        }
        if (dir == 1 && this.x + this.userAniWidth >= this.maxX) {
            dir = 0;
            this.x = this.maxX - this.userAniWidth;
        }
        this.walk(dir, 1, this.xspeed, this.xdelay);
    }

    void moveLeft() {
        this.dx = -1 * this.userDir;
    }

    void moveRight() {
        this.dx = 1 * this.userDir;
    }

    void moveUp() {
        if (this.y <= this.maxJumpHeight) {
            this.y = this.maxJumpHeight;
        } else if (System.currentTimeMillis() - this.jumpLastFrameTime > 10) {
            this.y = (int)((double)this.y - this.yspeed);
            this.jumpLastFrameTime = System.currentTimeMillis();
        }
    }

    void moveDown() {
        if (this.y >= this.minJumpHeight) {
            this.y = this.minJumpHeight;
        } else if (System.currentTimeMillis() - this.jumpLastFrameTime > 10) {
            this.y = (int)((double)this.y + this.yspeed);
            this.jumpLastFrameTime = System.currentTimeMillis();
        }
    }

    void checkWalls() {
        if (this.dx == -1 * this.userDir && this.x <= 0) {
            this.dx = 0;
            this.x = 0;
        }
        if (this.dx == 1 * this.userDir && this.x + this.userAniWidth >= this.maxX) {
            this.dx = 0;
            this.x = this.maxX - this.userAniWidth;
        }
        if (this.dx == 0) {
            if (this.x <= 0) {
                this.x = 0;
            } else if (this.x + this.userAniWidth >= this.maxX) {
                this.x = this.maxX - this.userAniWidth;
            }
        }
    }

    void crouch() {
        this.dx = 0;
    }

    void ground() {
        this.y = this.minJumpHeight;
    }
}