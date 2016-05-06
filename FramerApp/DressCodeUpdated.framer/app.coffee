# Import file "CompleteMockup"
frames = Framer.Importer.load("imported/CompleteMockup@1x")

# SETUP INFORMATION
# positioning information
frames.Sidebar.x = -524
frames.StylistMenu.y = 1230
frames.MainEmptyMenu.y = 1230
frames.MainFullMenu.y = 1230
frames.InspirationMenu.y = 1230
frames.AssistantMenu.y = 1230
frames.DonateMenu.y = 1230
frames.YearMenu.y = 1230
frames.MonthMenu.y = 1230
frames.SeasonMenu.y = 1230

frames.Header.y = -3

# initial visibility information
frames.MainFull.visible = false
frames.MainFullMenu.visible = false
frames.StylistFront.visible = false
frames.StylistOut.visible = false
frames.StylistMenu.visible = false
frames.Inspiration.visible = false
frames.InspirationMenu.visible = false
frames.Assistant.visible = false
frames.AssistantMenu.visible = false
frames.DonateMenu.visible = false
frames.JointCloset.visible = false
frames.YearReview.visible = false
frames.YearMenu.visible = false
frames.SeasonReview.visible = false
frames.SeasonMenu.visible = false
frames.MonthReview.visible = false
frames.MonthMenu.visible = false

# sidebar animation code
moveSidebar = (xPos) ->
	frames.Sidebar.animate
		properties:
			x: xPos
			curve: "linear"
frames.MainMenu.onClick ->
	moveSidebar(0)
	
frames.SidebarClose.onClick ->
	moveSidebar(-542)
	

# OUTFIT SECTION MENU NAVIGATION			
# navigate to today's outfit
frames.SidebarOutfit.onClick ->
	frames.MainEmpty.visible = false
	frames.StylistFront.visible = false
	frames.StylistMenu.visible = false
	frames.StylistOut.visible = false
	frames.MainEmptyMenu.visible = false
	frames.MainFull.visible = true
	frames.MainFullMenu.visible = true
	frames.Inspiration.visible = false
	frames.InspirationMenu.visible = false
	frames.Assistant.visible = false
	frames.AssistantMenu.visible = false
	frames.DonateMenu.visible = false
	frames.JointCloset.visible = false
	frames.YearReview.visible = false
	frames.YearMenu.visible = false
	frames.SeasonReview.visible = false
	frames.SeasonMenu.visible = false
	frames.MonthReview.visible = false
	frames.MonthMenu.visible = false
	moveSidebar(-542)
	
	
# STYLIST SECTION MENU NAVIGATION 
# navigate to the stylist
frames.SidebarStylist.onClick ->
	navigateToInspiration()
frames.InspirationMenuSuggestions.onClick ->
	navigateToStylist()
frames.AssistantMenuSuggestions.onClick ->
	navigateToStylist()
navigateToStylist = () ->
	frames.MainEmpty.visible = false
	frames.StylistFront.visible = true
	frames.StylistMenu.visible = true
	frames.StylistOut.visible = false
	frames.MainEmptyMenu.visible = false
	frames.MainFull.visible = false
	frames.MainFullMenu.visible = false
	frames.Inspiration.visible = false
	frames.InspirationMenu.visible = false
	frames.Assistant.visible = false
	frames.AssistantMenu.visible = false
	frames.DonateMenu.visible = false
	frames.JointCloset.visible = false
	frames.YearReview.visible = false
	frames.YearMenu.visible = false
	frames.SeasonReview.visible = false
	frames.SeasonMenu.visible = false
	frames.MonthReview.visible = false
	frames.MonthMenu.visible = false
	moveSidebar(-542)

# navigate into the going out section
frames.StylistFrontOut.onClick ->
	frames.MainEmpty.visible = false
	frames.StylistFront.visible = false
	frames.StylistMenu.visible = true
	frames.StylistOut.visible = true
	frames.MainEmptyMenu.visible = false
	frames.MainFull.visible = false
	frames.MainFullMenu.visible = false
	frames.Inspiration.visible = false
	frames.InspirationMenu.visible = false
	frames.Assistant.visible = false
	frames.AssistantMenu.visible = false
	frames.DonateMenu.visible = false
	frames.JointCloset.visible = false
	frames.YearReview.visible = false
	frames.YearMenu.visible = false
	frames.SeasonReview.visible = false
	frames.SeasonMenu.visible = false
	frames.MonthReview.visible = false
	frames.MonthMenu.visible = false
	moveSidebar(-542)

# navigate to the inspiration section
frames.StylistMenuInspiration.onClick ->
	navigateToInspiration()
frames.InspirationMenuInspiration.onClick ->
	navigateToInspiration()
frames.AssistantMenuInspiration.onClick ->
	navigateToInspiration()
navigateToInspiration = () ->
	frames.MainEmpty.visible = false
	frames.StylistFront.visible = false
	frames.StylistMenu.visible = false
	frames.StylistOut.visible = false
	frames.MainEmptyMenu.visible = false
	frames.MainFull.visible = false
	frames.MainFullMenu.visible = false
	frames.Inspiration.visible = true
	frames.InspirationMenu.visible = true
	frames.Assistant.visible = false
	frames.AssistantMenu.visible = false
	frames.DonateMenu.visible = false
	frames.JointCloset.visible = false
	frames.YearReview.visible = false
	frames.YearMenu.visible = false
	frames.SeasonReview.visible = false
	frames.SeasonMenu.visible = false
	frames.MonthReview.visible = false
	frames.MonthMenu.visible = false
	moveSidebar(-542)

# navigate to the assistant section
frames.StylistMenuAssistant.onClick ->
	navigateToAssistant()
frames.InspirationMenuAssistant.onClick ->
	navigateToAssistant()
frames.AssistantMenuAssistant.onClick ->
	navigateToAssistant()
navigateToAssistant = () ->
	frames.MainEmpty.visible = false
	frames.StylistFront.visible = false
	frames.StylistMenu.visible = false
	frames.StylistOut.visible = false
	frames.MainEmptyMenu.visible = false
	frames.MainFull.visible = false
	frames.MainFullMenu.visible = false
	frames.Inspiration.visible = false
	frames.InspirationMenu.visible = false
	frames.Assistant.visible = true
	frames.AssistantMenu.visible = true
	frames.DonateMenu.visible = false
	frames.JointCloset.visible = false
	frames.YearReview.visible = false
	frames.YearMenu.visible = false
	frames.SeasonReview.visible = false
	frames.SeasonMenu.visible = false
	frames.MonthReview.visible = false
	frames.MonthMenu.visible = false
	moveSidebar(-542)
	
	
# REVIEW SECTION MENU NAVIGATION
# navigate to the month review
frames.SidebarReview.onClick ->
	navigateToReview()
frames.SeasonMonth.onClick ->
	navigateToReview()
frames.YearMonth.onClick ->
	navigateToReview()
navigateToReview = () ->
	frames.MainEmpty.visible = false
	frames.StylistFront.visible = false
	frames.StylistMenu.visible = false
	frames.StylistOut.visible = false
	frames.MainEmptyMenu.visible = false
	frames.MainFull.visible = false
	frames.MainFullMenu.visible = false
	frames.Inspiration.visible = false
	frames.InspirationMenu.visible = false
	frames.Assistant.visible = false
	frames.AssistantMenu.visible = false
	frames.DonateMenu.visible = false
	frames.JointCloset.visible = false
	frames.YearReview.visible = false
	frames.YearMenu.visible = false
	frames.SeasonReview.visible = false
	frames.SeasonMenu.visible = false
	frames.MonthReview.visible = true
	frames.MonthMenu.visible = true
	moveSidebar(-542)

# navigate to the season review	
frames.MonthSeason.onClick ->
	navigateToSeason()
frames.YearSeason.onClick ->
	navigateToSeason()
navigateToSeason = () ->
	frames.MainEmpty.visible = false
	frames.StylistFront.visible = false
	frames.StylistMenu.visible = false
	frames.StylistOut.visible = false
	frames.MainEmptyMenu.visible = false
	frames.MainFull.visible = false
	frames.MainFullMenu.visible = false
	frames.Inspiration.visible = false
	frames.InspirationMenu.visible = false
	frames.Assistant.visible = false
	frames.AssistantMenu.visible = false
	frames.DonateMenu.visible = false
	frames.JointCloset.visible = false
	frames.YearReview.visible = false
	frames.YearMenu.visible = false
	frames.SeasonReview.visible = true
	frames.SeasonMenu.visible = true
	frames.MonthReview.visible = false
	frames.MonthMenu.visible = false
	moveSidebar(-542)

# navigate to the year review	
frames.MonthYear.onClick ->
	navigateToYear()
frames.SeasonYear.onClick ->
	navigateToYear()
navigateToYear = () ->
	frames.MainEmpty.visible = false
	frames.StylistFront.visible = false
	frames.StylistMenu.visible = false
	frames.StylistOut.visible = false
	frames.MainEmptyMenu.visible = false
	frames.MainFull.visible = false
	frames.MainFullMenu.visible = false
	frames.Inspiration.visible = false
	frames.InspirationMenu.visible = false
	frames.Assistant.visible = false
	frames.AssistantMenu.visible = false
	frames.DonateMenu.visible = false
	frames.JointCloset.visible = false
	frames.YearReview.visible = true
	frames.YearMenu.visible = true
	frames.SeasonReview.visible = false
	frames.SeasonMenu.visible = false
	frames.MonthReview.visible = false
	frames.MonthMenu.visible = false
	moveSidebar(-542)

# navigate to the donate portion	
frames.MonthDonate.onClick ->
	navigateToDonate()
frames.SeasonDonate.onClick ->
	navigateToDonate()
frames.YearDonate.onClick ->
	navigateToDonate()
navigateToDonate = () ->
	frames.MainEmpty.visible = false
	frames.StylistFront.visible = false
	frames.StylistMenu.visible = false
	frames.StylistOut.visible = false
	frames.MainEmptyMenu.visible = false
	frames.MainFull.visible = false
	frames.MainFullMenu.visible = false
	frames.Inspiration.visible = false
	frames.InspirationMenu.visible = false
	frames.Assistant.visible = false
	frames.AssistantMenu.visible = false
	frames.DonateMenu.visible = true
	frames.JointCloset.visible = true
	frames.YearReview.visible = false
	frames.YearMenu.visible = false
	frames.SeasonReview.visible = false
	frames.SeasonMenu.visible = false
	frames.MonthReview.visible = false
	frames.MonthMenu.visible = false
	moveSidebar(-542)


# DRAGGABILITY INFORMATION
# today's outfit
frames.MainFull.draggable.vertical = true
frames.MainFull.draggable.horizontal = false

# stylist suggestions
frames.StylistFront.draggable.vertical = true
frames.StylistFront.draggable.horizontal = false

# stylist inspiration
frames.Inspiration.draggable.vertical = true
frames.Inspiration.draggable.horizontal = false

# month review
frames.MonthReview.draggable.vertical = true
frames.MonthReview.draggable.horizontal = false

# season review
frames.SeasonReview.draggable.vertical = true
frames.SeasonReview.draggable.horizontal = false

# year review
frames.YearReview.draggable.vertical = true
frames.YearReview.draggable.horizontal = false

	






