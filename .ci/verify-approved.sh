#
# Copyright (c) parseva-math  2021.
# This is free and unencumbered software released into the public domain.
#
# Anyone is free to copy, modify, publish, use, compile, sell, or
# distribute this software, either in source code form or as a compiled
# binary, for any purpose, commercial or non-commercial, and by any
# means.
#
# In jurisdictions that recognize copyright laws, the author or authors
# of this software dedicate any and all copyright interest in the
# software to the public domain. We make this dedication for the benefit
# of the public at large and to the detriment of our heirs and
# successors. We intend this dedication to be an overt act of
# relinquishment in perpetuity of all present and future rights to this
# software under copyright law.
#
# THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
# EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
# MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
# IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR
# OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
# ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
# OTHER DEALINGS IN THE SOFTWARE.
#
# For more information, please refer to <http://unlicense.org/>
#

#!/bin/bash
# Attention, there is no "-x" to avoid problems on Travis
set -e

if [[ ! $PULL_REQUEST =~ ^([0-9]*)$ ]]; then exit 0; fi
LINK_COMMITS=https://api.github.com/repos/nmancus1/parseva-math/pulls/$PULL_REQUEST/commits
COMMITS=$(curl -s -H "Authorization: token $READ_ONLY_TOKEN" $LINK_COMMITS \
             | jq '.[0] | .commit.message')
echo 'Commit messages from github: '${COMMITS:0:60}...
ISSUE_NUMBER=$(echo $COMMITS | sed -e 's/^.*Issue //' | sed -e 's/:.*//')
echo 'Issue number: '$ISSUE_NUMBER && RESULT=0
if [[ $ISSUE_NUMBER =~ ^#[0-9]+$ ]]; then
    LINK_PR=https://api.github.com/repos/nmancus1/parseva-math/pulls/$PULL_REQUEST
    LINK_ISSUE=https://api.github.com/repos/nmancus1/parseva-math/issues/${ISSUE_NUMBER:1}
    REGEXP=($ISSUE_NUMBER\|https://github.com/nmancus1/parseva-math/issues/${ISSUE_NUMBER:1})
    PR_DESC=$(curl -s -H "Authorization: token $READ_ONLY_TOKEN" $LINK_PR \
                | jq '.body' | grep -E $REGEXP | cat )
    echo 'PR Description grepped:'${PR_DESC:0:80}
    if [[ -z $PR_DESC ]]; then
         echo 'Please put a reference to an Issue in the PR description,'
         echo 'this will bind the Issue to your PR in Github'
         RESULT=1;
       fi
    LABEL_APRV=$(curl -s -H "Authorization: token $READ_ONLY_TOKEN" $LINK_ISSUE \
                   | jq '.labels [] | .name' | grep approved | cat | wc -l )
    if [[ $LABEL_APRV == 0 ]]; then
         echo 'You are providing a PR for an Issue that is not approved yet,'
         echo 'please ask admins to approve your Issue first'
         RESULT=1;
    fi
  fi
if [[ $RESULT == 0 ]]; then
      echo 'PR validation succeeded.';
else
      echo 'PR validation failed.' && false;
fi
