package blackrusemod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

import blackrusemod.powers.SatellitePower;

public class ConvertAction extends AbstractGameAction {
	private int stack;
	public ConvertAction (int amount) {
		this.amount = amount;
		this.actionType = AbstractGameAction.ActionType.SPECIAL;
		this.target = AbstractDungeon.player;
		this.duration = com.megacrit.cardcrawl.core.Settings.ACTION_DUR_XFAST;
	}
	
	public void update() {
		if (this.target != null) {
			if (this.target.hasPower("KnivesPower")) {
				this.stack = Math.min(this.amount, this.target.getPower("KnivesPower").amount);
				AbstractDungeon.actionManager.addToTop(new ReducePowerAction(this.target, this.target, "KnivesPower", this.stack));
				AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this.target, this.target, new SatellitePower(this.target, this.stack), this.stack));
				if (this.target.hasPower("SurpressingFirePower")) {
					AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AbstractGameAction.AttackEffect.SHIELD));
					this.target.addBlock(this.target.getPower("SurpressingFirePower").amount*this.stack);
				}
			}
		}
		this.isDone = true;
	}
}