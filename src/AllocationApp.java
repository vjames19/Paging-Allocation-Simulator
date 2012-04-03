import javax.swing.SwingUtilities;

class AllocationApp
{

	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(
			new Runnable()
			{
				public void run()
				{
					new UserInterface();
				}
			}
		);
	}

}