SUMMARY = "SABnzbd - The automated Usenet download tool"
DESCRIPTION = "SABnzbd is an Open Source Binary Newsreader written in Python."
HOMEPAGE = "https://sabnzbd.org"
MAINTAINER = "team@sabnzbd.org"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYRIGHT.txt;md5=46890d8608abc5b2b4c8d09c1593f161"

DEPENDS = "${PYTHON_PN}"
RDEPENDS_${PN} = "\
    ${PYTHON_PN}-cheetah ${PYTHON_PN}-compression ${PYTHON_PN}-core ${PYTHON_PN}-crypt ${PYTHON_PN}-ctypes ${PYTHON_PN}-email ${PYTHON_PN}-html \
    ${PYTHON_PN}-misc ${PYTHON_PN}-multiprocessing ${PYTHON_PN}-sqlite3 ${PYTHON_PN}-shell ${PYTHON_PN}-sabyenc3 ${PYTHON_PN}-configobj \
    ${PYTHON_PN}-cryptography ${PYTHON_PN}-feedparser ${PYTHON_PN}-cheroot ${PYTHON_PN}-cherrypy ${PYTHON_PN}-portend ${PYTHON_PN}-chardet \
    ${PYTHON_PN}-notify2 ${PYTHON_PN}-puremagic ${PYTHON_PN}-guessit ${PYTHON_PN}-sgmllib3k ${PYTHON_PN}-more-itertools ${PYTHON_PN}-modules \
    "

RRECOMMENDS_${PN} = "par2cmdline unrar"

SRC_URI = "https://github.com/sabnzbd/sabnzbd/releases/download/${PV}/SABnzbd-${PV}-src.tar.gz \
	file://sabnzbd \
	file://sabnzbd.conf \
	file://init-functions \
	"

SRC_URI[md5sum] = "b3d1ca01039cb88f7c829412e3fd39f5"
SRC_URI[sha256sum] = "dfb92a91b6f466c31c9fc9ffc1a1d4b3e6352698674c5ad30913d7a69fefa221"

S = "${WORKDIR}/SABnzbd-${PV}"

INSTALLDIR = "${libdir}/${PN}"

PACKAGES = "${PN}-doc ${PN}-src ${PN}"

FILES_${PN}-src = "${INSTALLDIR}/*.py ${INSTALLDIR}/*/*.py ${INSTALLDIR}/*/*/*.py"
FILES_${PN}-doc = "${INSTALLDIR}/*.txt ${INSTALLDIR}/licenses ${INSTALLDIR}/interfaces/*/licenses"
FILES_${PN} = "${INSTALLDIR} /etc/init.d/sabnzbd /etc/init.d/init-functions /etc/enigma2/sabnzbd.conf"

inherit update-rc.d python3-compileall
INITSCRIPT_NAME = "sabnzbd"
INITSCRIPT_PARAMS = "defaults"

do_install() {
	install -d ${D}${INSTALLDIR}
	cp -r . ${D}${INSTALLDIR}/
	rm -rf ${D}${INSTALLDIR}/.git
	install -d ${D}${sysconfdir}/init.d
	install -m 755 ${WORKDIR}/sabnzbd ${D}${sysconfdir}/init.d/sabnzbd
	install -m 755 ${WORKDIR}/init-functions ${D}${sysconfdir}/init.d/init-functions
	install -d ${D}${sysconfdir}/enigma2
	install -m 644 ${WORKDIR}/sabnzbd.conf ${D}/etc/enigma2/sabnzbd.conf
}
