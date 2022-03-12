import sys
import requests
from concurrent.futures import ThreadPoolExecutor


def main(arg_params):
    if len(arg_params) < 2:
        raise "Usage: python main.py <url1> <url2> .. <urln>"
    arg_params.pop(0)
    url_list = ["https://"+url for url in arg_params]
    with ThreadPoolExecutor(max_workers=10) as pool:
        list(pool.map(send_request, url_list))


def send_request(url):
    print(url)
    response = requests.get(url)
    print("[{status}] {url}".format(status=response.status_code, url=url))
    return response.status_code


main(sys.argv)
