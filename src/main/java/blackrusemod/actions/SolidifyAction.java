package blackrusemod.actions;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.core.Settings.GameLanguage;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import blackrusemod.BlackRuseMod;
import blackrusemod.cards.TemporalArms;
import blackrusemod.cards.TemporalEssence;
import blackrusemod.cards.TemporalMisd;
import blackrusemod.cards.TemporalSlicing;

public class SolidifyAction extends AbstractGameAction {
	private ArrayList<AbstractCard> list = new ArrayList<AbstractCard>();
	private boolean canUpgrade;

	public SolidifyAction(AbstractCreature p, boolean canUpgrade) {
		this.actionType = AbstractGameAction.ActionType.SPECIAL;
		this.duration = Settings.ACTION_DUR_FAST;
		this.source = p;
		this.canUpgrade = canUpgrade;
		AbstractCard c;
		if (this.canUpgrade) {
			c = new TemporalSlicing().makeCopy();
			this.list.add(c);
			c = new TemporalMisd().makeCopy();
			this.list.add(c);
			c = new TemporalArms().makeCopy();
			this.list.add(c);
			c = new TemporalEssence().makeCopy();
			this.list.add(c);
		}
		else {
			c = new TemporalSlicing().makeCopy();
			c.upgrade();
			this.list.add(c);
			c = new TemporalMisd().makeCopy();
			c.upgrade();
			this.list.add(c);
			c = new TemporalArms().makeCopy();
			c.upgrade();
			this.list.add(c);
			c = new TemporalEssence().makeCopy();
			c.upgrade();
			this.list.add(c);
		}
	}

	public void update()
	{
		if (this.duration == Settings.ACTION_DUR_FAST) {
			if (Settings.language == GameLanguage.ZHS || Settings.language == GameLanguage.ZHT)
				BlackRuseMod.vs.open(this.list, null, "ѡ��һ�Ż�ʱ��");
			else BlackRuseMod.vs.open(this.list, null, "Choose a Temporal card");
			tickDuration();
			return;
		}

		if (BlackRuseMod.vs.prediction != null) {
			AbstractCard card = BlackRuseMod.vs.prediction.makeStatEquivalentCopy();
			AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(card, 1, false, false));
			this.isDone = true;
		}
		tickDuration();
	}
}