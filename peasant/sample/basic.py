from urllib.request import HTTPError
from urllib.request import urlopen

from bs4 import BeautifulSoup


def get_title(url):
    try:
        html = urlopen(url)
    except HTTPError as e:
        return None
    try:
        bs_obj = BeautifulSoup(html.read(), "html.parser")
        title = bs_obj.body.h1
    except AttributeError as e:
        return None
    return title


title = get_title("http://www.pythonscraping.com/pages/page1.html")
if title is None:
    print("Title could not be found")
else:
    print(title)
