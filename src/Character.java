import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintStream;

public class Character {
    Movement move;
    chooseCharacter avatar;
    Image pose;
    int hp;
    int delay;
    int userTiming = 0;
    int hitBoxWidth = 70;
    int hitBoxHeight = 100;
    int hitBoxY = 165;
    Rectangle hitBox;
    BufferedImage[] imageStore = null;
    boolean newImage = false;
    boolean hit = false;
    boolean block = false;
    boolean victim = false;
    boolean dead = false;
    boolean win1done = false;
    boolean spaceBarActive = false;
    boolean arrowRightPressed = false;
    boolean arrowLeftPressed = false;
    boolean downArrowPressed = false;
    boolean LPpressed = false;
    boolean MPpressed = false;
    boolean HPpressed = false;
    boolean LKpressed = false;
    boolean MKpressed = false;
    boolean HKpressed = false;

    Character(String getCharacter) throws IOException {
        if (getCharacter.equals("ryu")) {
            this.avatar = new Ryu();
        }
        if (getCharacter.equals("dhalsim")) {
            this.avatar = new Dhalsim();
        }
        this.move = new Movement();
        this.hp = this.avatar.hp;
        this.move.y = this.avatar.y;
        this.move.x = this.avatar.x;
        this.move.minJumpHeight = this.avatar.startAniY;
        this.move.maxJumpHeight = this.avatar.jumpHeight + this.avatar.AniHeight;
        this.move.userAniWidth = this.avatar.AniWidth;
        this.move.userAniHeight = this.avatar.AniHeight;
        this.delay = this.avatar.startAniDelay;
        this.setHitBox();
    }

    BufferedImage[] getCharImageArray() {
        this.setHitBoxHeight();
        this.setHitBoxWidth();
        this.move.checkWalls();
        this.checkDmgLvl();
        if (this.dead) {
            if (!this.win1done) {
                this.checkNew(this.avatar.win1);
                this.imageStore = this.avatar.win1;
                this.delay = 130;
                return this.avatar.win1;
            }
            this.checkNew(this.avatar.win2);
            this.imageStore = this.avatar.win2;
            this.delay = 130;
            return this.avatar.win2;
        }
        if (this.move.jumpDone) {
            this.avatar.AniHeight = this.avatar.jumpAniHeight;
            this.avatar.AniWidth = this.avatar.jumpAniWidth;
            this.checkNew(this.avatar.jump);
            this.imageStore = this.avatar.jump;
            this.delay = 85;
            return this.avatar.jump;
        }
        if (this.hit && !this.block) {
            this.checkNew(this.avatar.hitL);
            this.imageStore = this.avatar.hitL;
            this.delay = this.avatar.hitdelay;
            return this.avatar.hitL;
        }
        this.avatar.AniHeight = this.avatar.defaultHeight;
        this.avatar.AniWidth = this.avatar.defaultWidth;
        if (this.block) {
            this.checkNew(this.avatar.block);
            this.imageStore = this.avatar.block;
            this.delay = 155;
            return this.avatar.block;
        }
        if (this.move.attack.LP) {
            this.checkNew(this.avatar.LP);
            this.imageStore = this.avatar.LP;
            this.delay = this.avatar.LPdelay;
            return this.avatar.LP;
        }
        if (this.move.attack.MP) {
            this.checkNew(this.avatar.MP);
            this.imageStore = this.avatar.MP;
            this.delay = this.avatar.MPdelay;
            return this.avatar.MP;
        }
        if (this.move.attack.HP) {
            this.checkNew(this.avatar.HP);
            this.imageStore = this.avatar.HP;
            this.delay = this.avatar.HPdelay;
            return this.avatar.HP;
        }
        if (this.move.attack.LK) {
            this.checkNew(this.avatar.LK);
            this.imageStore = this.avatar.LK;
            this.delay = this.avatar.LKdelay;
            return this.avatar.LK;
        }
        if (this.move.attack.MK) {
            this.checkNew(this.avatar.MK);
            this.imageStore = this.avatar.MK;
            this.delay = this.avatar.MKdelay;
            return this.avatar.MK;
        }
        if (this.move.attack.HK) {
            this.checkNew(this.avatar.HK);
            this.imageStore = this.avatar.HK;
            this.delay = this.avatar.HKdelay;
            return this.avatar.HK;
        }
        if (this.downArrowPressed) {
            this.avatar.AniHeight = this.avatar.crouchHeight;
            this.avatar.AniWidth = this.avatar.crouchWidth;
            this.checkNew(this.avatar.crouch);
            this.imageStore = this.avatar.crouch;
            this.delay = 140;
            return this.avatar.crouch;
        }
        if (this.move.jumpR) {
            this.avatar.AniHeight = this.avatar.jumpRHeight;
            this.avatar.AniWidth = this.avatar.jumpRWidth;
            this.move.dx = 0;
            this.checkNew(this.avatar.jumpr);
            this.imageStore = this.avatar.jumpr;
            this.delay = 55;
            return this.avatar.jumpr;
        }
        if (this.move.jumpL) {
            this.avatar.AniHeight = this.avatar.jumpLHeight;
            this.avatar.AniWidth = this.avatar.jumpLWidth;
            this.checkNew(this.avatar.jumpl);
            this.imageStore = this.avatar.jumpl;
            this.delay = 55;
            return this.avatar.jumpl;
        }
        if (this.arrowRightPressed) {
            this.avatar.AniHeight = this.avatar.moveHeight;
            this.avatar.AniWidth = this.avatar.moveWidth;
            if (this.move.userDir == 1) {
                this.checkNew(this.avatar.moveR);
                this.imageStore = this.avatar.moveR;
                this.delay = 95;
                return this.avatar.moveR;
            }
            this.checkNew(this.avatar.moveL);
            this.imageStore = this.avatar.moveL;
            this.delay = 95;
            return this.avatar.moveL;
        }
        if (this.arrowLeftPressed) {
            this.avatar.AniHeight = this.avatar.moveHeight;
            this.avatar.AniWidth = this.avatar.moveWidth;
            if (this.move.userDir == -1) {
                this.checkNew(this.avatar.moveR);
                this.imageStore = this.avatar.moveR;
                this.delay = 95;
                return this.avatar.moveR;
            }
            this.checkNew(this.avatar.moveL);
            this.imageStore = this.avatar.moveL;
            this.delay = 95;
            return this.avatar.moveL;
        }
        this.checkNew(this.avatar.stanceAni);
        this.imageStore = this.avatar.stanceAni;
        this.delay = 85;
        this.avatar.AniHeight = this.avatar.stanceHeight;
        this.avatar.AniWidth = this.avatar.stanceWidth;
        return this.avatar.stanceAni;
    }

    void checkEdges() {
        if (this.move.x < 0) {
            this.move.x = 0;
        } else if (this.move.x > 530) {
            this.move.x = 530;
        }
    }

    void checkNew(BufferedImage[] currentImage) {
        this.newImage = this.imageStore != currentImage;
    }

    void leftArrowPressed() {
        if (!this.checkJumpFalse() && !this.downArrowPressed) {
            if (this.arrowRightPressed) {
                this.move.dx = 0;
                this.arrowRightPressed = false;
            } else if (this.move.userDir == 1 && this.victim) {
                this.block = true;
                this.move.dx = 0;
            } else {
                this.move.moveLeft();
                this.arrowLeftPressed = true;
            }
        }
    }

    void rightArrowPressed() {
        if (!this.downArrowPressed && !this.checkJumpFalse()) {
            if (this.arrowLeftPressed) {
                this.move.dx = 0;
                this.arrowLeftPressed = false;
            } else if (this.move.userDir == -1 && this.victim) {
                this.block = true;
                this.move.dx = 0;
            } else {
                this.move.moveRight();
                this.arrowRightPressed = true;
            }
        }
    }

    void upArrowPressed() {
        if (!this.downArrowPressed && !this.checkJumpFalse()) {
            if (this.move.dx == 0) {
                this.move.jumpDone = true;
            } else if (this.move.dx == 1) {
                this.move.jumpR = true;
                this.move.startMoveJump();
                this.move.dx = 0;
            } else if (this.move.dx == -1) {
                this.move.jumpL = true;
                this.move.startMoveJump();
                this.move.dx = 0;
            }
        }
    }

    void downArrowPressed() {
        if (!this.downArrowPressed && !this.checkJumpFalse()) {
            this.downArrowPressed = true;
            this.move.crouch();
        }
    }

    void lightPunchPressed() {
        if (!this.checkAttackFalse() && !this.checkJumpFalse()) {
            this.move.attack.lightPunch();
        }
        this.LPpressed = true;
    }

    void mediumPunchPressed() {
        if (!this.checkAttackFalse() && !this.checkJumpFalse()) {
            this.move.attack.midPunch();
        }
        this.MPpressed = true;
    }

    void hardPunchPressed() {
        if (!this.checkAttackFalse() && !this.checkJumpFalse()) {
            this.move.attack.hardPunch();
        }
        this.HPpressed = true;
    }

    void lightKickPressed() {
        if (!this.checkAttackFalse() && !this.checkJumpFalse()) {
            this.move.attack.lightKick();
        }
        this.LKpressed = true;
    }

    void mediumKickPressed() {
        if (!this.checkAttackFalse() && !this.checkJumpFalse()) {
            this.move.attack.midKick();
        }
        this.MKpressed = true;
    }

    void hardKickPressed() {
        if (!this.checkAttackFalse() && !this.checkJumpFalse()) {
            this.move.attack.hardKick();
        }
        this.HKpressed = true;
    }

    void checkStats() {
        System.out.println("move.x: " + this.move.x + "  y: " + this.move.y + "  dx: " + this.move.dx + "  dxstore: " + this.move.dxstore + "  hp: " + this.hp + "/" + this.avatar.maxhp + "  userDir: " + this.move.userDir + "  HK: " + this.move.attack.HK);
    }

    void poison() {
        --this.hp;
    }

    void heal() {
        int hpBar = this.hp / this.avatar.maxhp * 100;
        if (hpBar >= 100) {
            System.out.println("HP is maxed!!");
        } else if (this.hp + 25 > this.avatar.maxhp) {
            this.hp = this.avatar.maxhp;
            System.out.println("Used Health Potion!!  HP is fully restored!!");
        } else {
            this.hp += 25;
            System.out.println("Used Health Potion!!");
        }
    }

    void quit() {
        System.exit(0);
    }

    void leftArrowReleased() {
        this.arrowLeftPressed = false;
        this.move.dx = 0;
        this.block = false;
    }

    void rightArrowReleased() {
        this.arrowRightPressed = false;
        this.move.dx = 0;
        this.block = false;
    }

    void lightPunchReleased() {
        this.LPpressed = false;
    }

    void mediumPunchReleased() {
        this.MPpressed = false;
    }

    void hardPunchReleased() {
        this.HPpressed = false;
    }

    void lightKickReleased() {
        this.LKpressed = false;
    }

    void mediumKickReleased() {
        this.MKpressed = false;
    }

    void hardKickReleased() {
        this.HKpressed = false;
    }

    void upArrowReleased() {
        if (this.arrowRightPressed) {
            this.move.dx = 1;
        } else if (this.arrowLeftPressed) {
            this.move.dx = -1;
        }
    }

    void downArrowReleased() {
        if (this.downArrowPressed) {
            this.downArrowPressed = false;
            this.block = false;
        }
    }

    boolean checkFalse() {
        if (this.arrowRightPressed || this.arrowLeftPressed || this.downArrowPressed || this.LPpressed || this.move.jumpDone || this.move.jumpL || this.move.jumpR || this.move.attack.checkFalse()) {
            return true;
        }
        return false;
    }

    boolean checkJumpFalse() {
        if (this.move.jumpDone || this.move.jumpL || this.move.jumpR) {
            return true;
        }
        return false;
    }

    boolean checkAttackFalse() {
        if (this.LPpressed || this.MPpressed || this.HPpressed || this.LKpressed || this.MKpressed || this.HKpressed || this.downArrowPressed) {
            return true;
        }
        return false;
    }

    boolean checkDeath(int userDied) {
        if (this.hp <= 0) {
            return true;
        }
        return false;
    }

    void setHitBox() {
        this.setHitBoxWidth();
        this.setHitBoxHeight();
        this.setHitBoxY();
        this.hitBox = new Rectangle(this.move.x + 7 + (- this.move.userDir) * 5, this.hitBoxY, this.hitBoxWidth, this.hitBoxHeight);
        this.move.userAniHeight = this.avatar.AniHeight;
        this.move.userAniWidth = this.avatar.AniWidth;
    }

    void setHitBoxWidth() {
        this.hitBoxWidth = this.avatar.AniWidth - 20;
    }

    void setHitBoxHeight() {
        this.hitBoxHeight = this.downArrowPressed ? 66 : this.avatar.AniHeight;
        if (this.move.y < 160) {
            this.hitBoxHeight = 50;
        }
    }

    void setHitBoxY() {
        this.hitBoxY = this.move.y - this.hitBoxHeight;
    }

    void checkDmgLvl() {
        this.move.dmgLvl = this.move.attack.LP || this.move.attack.LK ? 1 : (this.move.attack.MP || this.move.attack.MK ? 2 : (this.move.attack.HP || this.move.attack.HK ? 3 : 0));
    }

    int getX() {
        return this.move.x;
    }

    int getY() {
        return this.move.y;
    }

    int getHP() {
        return this.hp;
    }
}