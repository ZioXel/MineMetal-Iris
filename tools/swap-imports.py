#!/usr/bin/env python3
"""Point Iris sources at the MineMetal GL facades (idempotent). Run after merging upstream."""
import glob, os, re, sys
os.chdir(os.path.join(os.path.dirname(__file__), '..', 'common', 'src', 'main', 'java'))
root = 'net/irisshaders/iris'
pkg = 'net.irisshaders.iris.metal.gl'
core = re.compile(r'^import org\.lwjgl\.opengl\.(GL\d+C?);[ \t]*$', re.M)
gsm = re.compile(r'^import com\.mojang\.renderpearl\.backend\.opengl\.GlStateManager;[ \t]*$', re.M)
missing, changed = set(), 0
for path in glob.glob(root + '/**/*.java', recursive=True):
    if path.startswith(root + '/metal/'):
        continue
    raw = open(path, 'rb').read().decode('utf-8')
    s = core.sub(lambda m: (missing.add(m.group(1)) or f'import {pkg}.{m.group(1)};'), raw)
    if '/mixin/' not in path:
        s = gsm.sub(f'import {pkg}.GlStateManager;', s)
    if s != raw:
        open(path, 'wb').write(s.encode('utf-8'))
        changed += 1
facades = {os.path.basename(p)[:-5] for p in glob.glob(root + '/metal/gl/*.java')}
need = sorted(missing - facades)
print(f'{changed} files updated')
if need:
    print('Create facades for:', ', '.join(need), file=sys.stderr)
    sys.exit(1)
