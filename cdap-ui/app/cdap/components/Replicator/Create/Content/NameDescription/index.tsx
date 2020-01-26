/*
 * Copyright © 2020 Cask Data, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

import * as React from 'react';
import withStyles, { WithStyles, StyleRules } from '@material-ui/core/styles/withStyles';
import { createContextConnect, ICreateContext } from 'components/Replicator/Create';
import WidgetWrapper from 'components/ConfigurationGroup/WidgetWrapper';

const styles = (): StyleRules => {
  return {
    root: {
      padding: '30px 40px',
      width: '50%',
      maxWidth: '1000px',
      minWidth: '600px',
    },
  };
};

const Name = ({ setName, value }) => {
  const widget = {
    label: 'Name',
    'widget-type': 'textbox',
    'widget-attributes': {
      placeholder: 'Select a name for the replicator',
    },
  };

  const property = {
    required: true,
    name: 'name',
  };

  return (
    <WidgetWrapper
      widgetProperty={widget}
      pluginProperty={property}
      value={value}
      onChange={setName}
    />
  );
};

const Description = ({ setDescription, value }) => {
  const widget = {
    label: 'Description',
    'widget-type': 'textarea',
    'widget-attributes': {
      placeholder: '240 character max',
    },
  };

  const property = {
    required: false,
    name: 'description',
  };

  return (
    <WidgetWrapper
      widgetProperty={widget}
      pluginProperty={property}
      value={value}
      onChange={setDescription}
    />
  );
};

type INameDescriptionProps = ICreateContext & WithStyles<typeof styles>;

const NameDescriptionView: React.FC<INameDescriptionProps> = ({ classes, name, description }) => {
  const [localName, setLocalName] = React.useState(name);
  const [localDescription, setLocalDescription] = React.useState(description);

  return (
    <div className={classes.root}>
      <h3>Name Replicator</h3>
      <pre>{localName}</pre>
      <Name value={localName} setName={setLocalName} />
      <br />
      <br />
      <Description value={localDescription} setDescription={setLocalDescription} />
    </div>
  );
};

const StyledNameDescription = withStyles(styles)(NameDescriptionView);
const NameDescription = createContextConnect(StyledNameDescription);
export default NameDescription;
