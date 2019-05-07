package rat.poison.scripts.esp

import rat.poison.curSettings
import rat.poison.strToBool

fun esp() {
	if (!curSettings["ENABLE_ESP"]!!.strToBool()) return

	glowEsp()
	boxEsp()
	skeletonEsp()
	chamsEsp()
	hitSoundEsp()
	radarEsp()
}