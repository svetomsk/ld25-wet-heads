package tool;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import main.Game;
import main.World;
import entity.Entity;

public class EntitiesEditorTool extends Tool
{
	@Override
	public void useClicked(World world, long x, long y)
	{
		final JPanel content = new JPanel();
		content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
		final Entity e = world.getEntity(x, y);
		if(e == null) return;
		e.fillEditPanel(content);
		
		content.addAncestorListener(new AncestorListener()
		{
			@Override
			public void ancestorRemoved(AncestorEvent arg0) 
			{
				e.saveEditPanelChanges(content);
			}
			@Override
			public void ancestorMoved(AncestorEvent arg0) {}
			@Override
			public void ancestorAdded(AncestorEvent arg0) {}
		});
		
		Game.throwFlowingFrame(content, (int) ((x-Game.x)/Game.scale), (int) ((y-Game.y)/Game.scale), -1, -1, Game.FFRAME1);
	}
	@Override
	public String getName()
	{
		return "Entities editor";
	}
}
