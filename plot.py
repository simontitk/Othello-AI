import matplotlib.pyplot as plt
import pandas as pd
import os


PATH = "./logs/"
SKIP_LAST = 0
SKIP_FIRST = 13

files = sorted(os.listdir(path=PATH))

fig, axs = plt.subplots(nrows=1, ncols=len(files)-SKIP_LAST-SKIP_FIRST, figsize=(20, 6))

for i, csv in enumerate(files[SKIP_FIRST:len(files)-SKIP_LAST]):
    df = pd.read_csv(PATH+csv, sep=",", names=["move", "time", "legal moves"])
    axs[i].bar(df["move"], df["time"])
    axs[i].set_xlabel("move [-]")
    axs[i].set_ylabel("time [ms]")
    axs[i].set_title(f"depth={csv[-5]}")

    ax2 = axs[i].twinx()
    ax2.plot(df["move"], df["legal moves"], color="orange")
    ax2.set_ylim(0, 21)
    ax2.set_yticks(range(0, 21, 1))
    ax2.set_ylabel("legal moves [-]")

fig.tight_layout()
plt.show()