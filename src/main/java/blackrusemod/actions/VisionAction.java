package blackrusemod.actions;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.core.Settings.GameLanguage;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import blackrusemod.BlackRuseMod;
import blackrusemod.cards.Deadline;
import blackrusemod.cards.Read;
import blackrusemod.cards.ReturningBlade;
import blackrusemod.cards.Snipe;
import blackrusemod.cards.TimeTheft;
import blackrusemod.cards._DummyAttack;
import blackrusemod.cards._DummyNotAttack;
import blackrusemod.powers.DeadlinePower;
import blackrusemod.powers.ReadPower;
import blackrusemod.powers.ReturningBladePower;
import blackrusemod.powers.SnipePower;
import blackrusemod.powers.TimeTheftPower;

public class VisionAction extends AbstractGameAction {
	private boolean prediction;
	private ArrayList<AbstractCard> list = new ArrayList<AbstractCard>();
	private int amount2;
	private AbstractCard card;

	public VisionAction(AbstractCreature p, AbstractCreature m, int amount, int amount2, AbstractCard card) {
		this.actionType = AbstractGameAction.ActionType.SPECIAL;
		this.duration = Settings.ACTION_DUR_FAST;
		this.source = p;
		this.target = m;
		this.amount = amount;
		this.amount2 = amount2;
		this.card = card;
		AbstractCard c = new _DummyAttack();
		this.list.add(c);
		c = new _DummyNotAttack();
		this.list.add(c);
	}

	public void update()
	{
		if (this.duration == Settings.ACTION_DUR_FAST) {
			if (Settings.language == GameLanguage.ZHS || Settings.language == GameLanguage.ZHT)
				BlackRuseMod.vs.open(this.list, null, "敌人的意图是？");
			else BlackRuseMod.vs.open(this.list, null, "The enemy's intent will be?");
			tickDuration();
			return;
		}

		if (BlackRuseMod.vs.prediction.cardID == "_DummyAttack") this.prediction = true;
		else if (BlackRuseMod.vs.prediction.cardID == "_DummyNotAttack") this.prediction = false;
		
		if (this.card instanceof Read) {
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.source, this.source, new ReadPower(this.source, this.target, this.amount, this.amount2, this.prediction), this.amount));
			this.isDone = true;
		}
		if (this.card instanceof Snipe) {
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.source, this.source, new SnipePower(this.source, this.target, this.amount, this.prediction), this.amount));
			this.isDone = true;
		}
		if (this.card instanceof TimeTheft) {
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.source, this.source, new TimeTheftPower(this.source, this.target, this.amount, this.prediction), this.amount));
			this.isDone = true;
		}
		if (this.card instanceof Deadline) {
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.source, this.source, new DeadlinePower(this.source, this.target, this.amount, this.prediction), this.amount));
			this.isDone = true;
		}
		if (this.card instanceof ReturningBlade) {
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.source, this.source, new ReturningBladePower(this.source, this.target, this.amount, this.prediction, this.card), this.amount));
			this.isDone = true;
		}

		tickDuration();
	}
}