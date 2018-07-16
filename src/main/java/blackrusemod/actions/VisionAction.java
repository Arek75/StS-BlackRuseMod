package blackrusemod.actions;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.core.Settings.GameLanguage;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import blackrusemod.BlackRuseMod;
import blackrusemod.cards._DummyAttack;
import blackrusemod.cards._DummyNotAttack;
import blackrusemod.powers.NoEscapePower;
import blackrusemod.powers.ReadPower;
import blackrusemod.powers.SnipePower;
import blackrusemod.powers.TauntPower;

public class VisionAction extends AbstractGameAction {
	private String effects;
	private boolean prediction;
	private ArrayList<AbstractCard> list = new ArrayList<AbstractCard>();
	private int amount2;

	public VisionAction(AbstractCreature p, AbstractCreature m, int amount, int amount2, String EFFECTS) {
		this.actionType = AbstractGameAction.ActionType.SPECIAL;
		this.duration = Settings.ACTION_DUR_FAST;
		this.effects = EFFECTS;
		this.source = p;
		this.target = m;
		this.amount = amount;
		this.amount2 = amount2;
		AbstractCard c = new _DummyAttack();
		this.list.add(c);
		c = new _DummyNotAttack();
		this.list.add(c);
	}

	public void update()
	{
		if (this.duration == Settings.ACTION_DUR_FAST) {
			if (Settings.language == GameLanguage.ZHS || Settings.language == GameLanguage.ZHT)
				BlackRuseMod.vs.open(this.list, null, "���˵���ͼ���ǣ�");
			else BlackRuseMod.vs.open(this.list, null, "The enemy's intent will be?");
			tickDuration();
			return;
		}

		if (BlackRuseMod.vs.prediction.cardID == "_DummyAttack") this.prediction = true;
		else if (BlackRuseMod.vs.prediction.cardID == "_DummyNotAttack") this.prediction = false;
		
		if (this.effects == "Read") {
			if (this.source.hasPower("ReadPower")) AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.source, this.source, "ReadPower"));
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.source, this.source, new ReadPower(this.source, this.target, this.amount, this.prediction), this.amount));
			this.isDone = true;
		}
		
		if (this.effects == "Snipe") {
			if (this.source.hasPower("SnipePower")) AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.source, this.source, "SnipePower"));
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.source, this.source, new SnipePower(this.source, this.target, this.amount, this.prediction), this.amount));
			this.isDone = true;
		}
		
		if (this.effects == "Taunt") {
			if (this.source.hasPower("TauntPower")) AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.source, this.source, "TauntPower"));
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.source, this.source, new TauntPower(this.source, this.target, this.amount, this.prediction), this.amount));
			this.isDone = true;
		}
		
		if (this.effects == "NoEscape") {
			if (this.source.hasPower("NoEscapePower")) AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.source, this.source, "NoEscapePower"));
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.source, this.source, new NoEscapePower(this.source, this.target, this.amount, this.amount2, this.prediction), this.amount));
			this.isDone = true;
		}
	

		tickDuration();
	}
}